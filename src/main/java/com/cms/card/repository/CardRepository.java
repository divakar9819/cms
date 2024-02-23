package com.cms.card.repository;

import com.cms.card.entity.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Divakar Verma
 * @created_at : 21/02/2024 - 1:25 pm
 * @mail_to: vermadivakar2022@gmail.com
 */
@Repository
public interface CardRepository extends ReactiveCrudRepository<Card,String> {

    public Mono<Card> findByUsername(String username);
}
