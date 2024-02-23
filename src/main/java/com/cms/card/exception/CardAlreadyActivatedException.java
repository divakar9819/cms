package com.cms.card.exception;

import com.cms.card.payload.request.CardRequest;

/**
 * @author Divakar Verma
 * @created_at : 22/02/2024 - 4:53 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
public class CardAlreadyActivatedException extends RuntimeException{

    public CardAlreadyActivatedException(String message){
        super(message);
    }
}
