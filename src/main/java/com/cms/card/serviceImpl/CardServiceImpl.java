package com.cms.card.serviceImpl;

import com.cms.card.entity.Card;
import com.cms.card.exception.*;
import com.cms.card.payload.request.CardRequest;
import com.cms.card.payload.request.WalletRequest;
import com.cms.card.payload.response.CardResponse;
import com.cms.card.payload.response.ValidTokenResponse;
import com.cms.card.payload.response.WalletResponse;
import com.cms.card.repository.CardRepository;
import com.cms.card.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.cms.card.interceptor.AuthenticationInterceptor.getAuthToken;
import static com.cms.card.interceptor.AuthenticationInterceptor.getUsername;
import static com.cms.card.utils.Util.*;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:24 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("getOnboardingWebClient")
    public WebClient onboardingWebClient;

    @Override
    public Mono<CardResponse> cardRegistration() {
        String username = getUsername();
        Card card = new Card();
        card.setCardId(getRandomCardId());
        card.setCardNumber(getRandomCardNumber());
        card.setCvv(getRandomCVV());
        LocalDate expiry = LocalDate.now();
        card.setExpiryDate(expiry.plusYears(5));
        WalletRequest walletRequest = new WalletRequest(username,card.getCardId());
        return activateCard(walletRequest)
                .flatMap(walletResponse -> {
                   if(walletResponse.getCardStatus().equalsIgnoreCase("activated")){
                       card.setWalletId(walletResponse.getWalletId());
                       card.setCardHolderName(walletResponse.getName());
                       card.setUsername(username);
                       card.setActivated(true);
                       return cardRepository.save(card)
                               .flatMap(createdCard -> Mono.just(cardToCardResponse(createdCard)))
                               .switchIfEmpty(Mono.error(new GlobalException("Getting error on card registration")));
                   }
                   else {
                       return Mono.error(new GlobalException("User wallet is not activated"));
                   }
                })
                .onErrorResume(Mono::error);
//        return cardRepository.save(card)
//                .flatMap(createdCard -> Mono.just(cardToCardResponse(createdCard)))
//                .switchIfEmpty(Mono.error(new GlobalException("Getting error on card registration")));
    }

    @Override
    public Mono<Card> getCardByUsername(String username) {
        return cardRepository.findByUsername(username)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Card data not found")));
    }

    public Mono<WalletResponse> activateCard(WalletRequest walletRequest) {
        return onboardingWebClient.post().uri("/activateCard")
                .body(Mono.just(walletRequest),WalletRequest.class)
                .header(HttpHeaders.AUTHORIZATION, getAuthToken())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.CONFLICT) {
                        return Mono.error(new CardAlreadyActivatedException("Card already activated"));
                    } else {
                        return Mono.error(new InvalidTokenException("Invalid token"));
                    }
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ServerErrorException("Server error"))
                )
                .onStatus(HttpStatusCode :: isError, clientResponse -> {
                    if(clientResponse.statusCode()== HttpStatus.SERVICE_UNAVAILABLE){
                        return Mono.error(new ConnectionRefusedException("Connection refused"));
                    }
                    else {
                        return  clientResponse.createException();
                    }
                })
                .bodyToMono(WalletResponse.class);
    }

    public Card cardRequestToCard(CardRequest cardRequest) {
        return this.modelMapper.map(cardRequest,Card.class);
    }

    public CardResponse cardToCardResponse(Card card) {
        return this.modelMapper.map(card,CardResponse.class);
    }
}
