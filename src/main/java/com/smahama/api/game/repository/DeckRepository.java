package com.smahama.api.game.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smahama.api.game.model.Deck;

/**
 * Repository for {@link Deck}
 * @author Salomon Mahama
 *
 */
@Repository
public class DeckRepository {

    private final List<Deck> decks = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private CardRepository cardRepository;

    private final AtomicInteger deckIdsHolder = new AtomicInteger(1);

    private DeckRepository() {

        //
    }

    public Deck createDeck(
        final String deckName) {

        final var deck = new Deck(this.deckIdsHolder.getAndIncrement());
        final var cards = this.cardRepository.getAllCards();
        deck.getCards().addAll(cards);
        deck.setDeckName(deckName);
        this.decks.add(deck);

        return deck;
    }

    public void deleteDeck(
        final Deck deck) {

        this.decks.remove(deck);
    }

    public List<Deck> getAllDecks() {

        return Collections.unmodifiableList(this.decks);
    }
}
