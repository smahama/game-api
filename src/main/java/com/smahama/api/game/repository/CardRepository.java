package com.smahama.api.game.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Rank;
import com.smahama.api.game.model.Suit;

/**
 * Repository for {@link Card}
 * @author Salomon Mahama
 *
 */
@Repository
public class CardRepository {

    private static final List<Card> PLAY_CARDS = new ArrayList<>(52);

    static {
        initCards();
    }

    private CardRepository() {

        //
    }

    private static void initCards() {

        final AtomicInteger cardIdsHolder = new AtomicInteger(1);
        for (final Suit suit : Suit.values()) {
            for (final Rank rank : Rank.values()) {
                PLAY_CARDS.add(new Card(cardIdsHolder.getAndIncrement(), suit, rank));
            }
        }
    }

    public List<Card> getAllCards() {

        return Collections.unmodifiableList(PLAY_CARDS);
    }
}
