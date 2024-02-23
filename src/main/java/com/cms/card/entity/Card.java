package com.cms.card.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:14 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@Document(collection = "card_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {

    @Id
    private String cardId;
    private String walletId;
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private LocalDate expiryDate;
    private boolean isActivated;
    private String username;
}
