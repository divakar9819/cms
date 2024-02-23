package com.cms.card.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Divakar Verma
 * @created_at : 22/02/2024 - 3:53 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletRequest {

    private String username;
    private String cardId;
}
