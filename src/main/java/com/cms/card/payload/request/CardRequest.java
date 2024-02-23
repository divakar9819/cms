package com.cms.card.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:17 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardRequest {

    private String cardId;
    private String walletId;
    private String cardHolderName;
    private String username;
}
