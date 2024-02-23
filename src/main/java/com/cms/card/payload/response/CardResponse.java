package com.cms.card.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:20 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardResponse {
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;
}
