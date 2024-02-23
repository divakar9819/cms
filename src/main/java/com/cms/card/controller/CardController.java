package com.cms.card.controller;

import com.cms.card.entity.Card;
import com.cms.card.exception.CardAlreadyActivatedException;
import com.cms.card.payload.request.CardRequest;
import com.cms.card.payload.response.CardResponse;
import com.cms.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:08 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@RestController
@RequestMapping("api/v1/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/home")
    public String home(){
        return "Card home";
    }

    @PostMapping("/activateCard")
    public Mono<ResponseEntity<CardResponse>> cardRegistration() {
    return cardService.cardRegistration()
          .map(cardResponse -> ResponseEntity.status(HttpStatus.CREATED).body(cardResponse))
            .onErrorResume(throwable -> Mono.error(throwable))
          .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/getCardData/{username}")
    public Mono<ResponseEntity<Card>> getCardData(@PathVariable String username){
        return cardService.getCardByUsername(username)
                .map(cardResponse -> ResponseEntity.status(HttpStatus.OK).body(cardResponse))
                .onErrorResume(throwable -> {
                    if (throwable instanceof CardAlreadyActivatedException){
                        return Mono.error(throwable);
                    }
                    else {
                        return Mono.error(throwable);
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }


}
