package com.cms.card.service;

import com.cms.card.entity.Card;
import com.cms.card.payload.request.CardRequest;
import com.cms.card.payload.response.CardResponse;
import reactor.core.publisher.Mono;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:17 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
public interface CardService {

    public Mono<CardResponse> cardRegistration();

    public Mono<Card> getCardByUsername(String username);
}
