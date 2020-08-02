package com.smahama.api.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smahama.api.game.dto.CardCount;
import com.smahama.api.game.dto.CreateGameRequest;
import com.smahama.api.game.dto.SuitCount;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.helper.ListShuffleHelper;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.repository.DeckRepository;
import com.smahama.api.game.repository.GameRepository;
import com.smahama.api.game.repository.PlayerRepository;
import com.smahama.api.game.service.GameService;

/**
 *  Class implementation of {@link GameService}
 * @author Salomon Mahama
 *
 */
@Service
public class GameServiceImpl
    implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ListShuffleHelper listShuffleHelper;

    private GameServiceImpl() {

        //
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game createGame(
        final CreateGameRequest createGameRequest) {

        return this.gameRepository.createGame(createGameRequest.getGameName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGame(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getShoe().getDecks().stream().forEach(deck -> this.deckRepository.deleteDeck(deck));
        game.getPlayers().stream().forEach(player -> this.playerRepository.deletePlayer(player));
        this.gameRepository.deleteGame(game.getGameId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game addDeck(
        final Integer gameId,
        final Deck deck)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        this.gameRepository.addDeck(game, deck);

        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game addPlayer(
        final Integer gameId,
        final Player player)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        this.gameRepository.addPlayer(game, player);

        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(
        final Integer gameId,
        final Player player)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        this.gameRepository.removePlayer(game, player.getPlayerId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var shoe = game.getShoe();
        if (shoe != null) {
            this.listShuffleHelper.shuffle(shoe.getCards());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SuitCount> getCardCountBySuit(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var shoe = game.getShoe();
        final var suitCounts = new ArrayList<SuitCount>();

        for (final var card : shoe.getCards()) {
            final var optionalSuitCount =
                suitCounts.stream().filter(suitCount -> suitCount.getSuit() == card.getSuit()).findFirst();
            if (optionalSuitCount.isPresent()) {
                optionalSuitCount.get().getCount().incrementAndGet();
            } else {
                final var suitCount = new SuitCount();
                suitCount.setSuit(card.getSuit());
                suitCount.getCount().incrementAndGet();
                suitCounts.add(suitCount);
            }
        }

        return suitCounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardCount> getCardCount(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var shoe = game.getShoe();
        final var cardCounts = new ArrayList<CardCount>();
        final var sortedCards = new ArrayList<>(shoe.getCards());
        Collections.sort(sortedCards);

        for (final var card : sortedCards) {
            final var optionalCardCount = cardCounts.stream().filter(filter -> filter.getCard().equals(card)).findAny();

            if (optionalCardCount.isPresent()) {
                optionalCardCount.get().getCount().incrementAndGet();
            } else {
                final CardCount cardCount = new CardCount();
                cardCount.setCard(card);
                cardCount.getCount().incrementAndGet();
                cardCounts.add(cardCount);
            }
        }

        return cardCounts;
    }
}
