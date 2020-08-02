package com.smahama.api.game.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Deck;
import com.smahama.api.game.model.Game;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.model.Shoe;

/**
 * Repository for {@link Game}
 * @author Salomon Mahama
 *
 */
@Repository
public class GameRepository {

    private final List<Game> games = Collections.synchronizedList(new ArrayList<>());

    private final AtomicInteger gameIdsHolder = new AtomicInteger(1);

    private GameRepository() {

        //
    }

    public Game createGame(
        final String gameName) {

        final var game = new Game(this.gameIdsHolder.getAndIncrement());
        game.setGameName(gameName);
        game.setShoe(new Shoe());
        this.games.add(game);

        return game;
    }

    public Optional<Game> getGame(
        final Integer gameId) {

        return this.games.stream().filter(game -> game.getGameId().equals(gameId)).findFirst();
    }

    public void deleteGame(
        final Integer gameId) {

        this.games.removeIf(game -> game.getGameId().equals(gameId));
    }

    public void addDeck(
        final Game game,
        final Deck deck) {

        game.getShoe().addDeck(deck);
    }

    public void addPlayer(
        final Game game,
        final Player player) {

        game.getPlayers().add(player);
    }

    public void removePlayer(
        final Game game,
        final Integer playerId) {

        game.getPlayers().removeIf(player -> player.getPlayerId().equals(playerId));
    }

    public List<Game> getAllGames() {

        return Collections.unmodifiableList(this.games);
    }

    public Card getNextCard(
        final Game game) {

        final var cards = game.getShoe().getCards();
        if (cards.isEmpty()) {
            return null;
        }
        final var card = cards.remove(0);
        return card;
    }
}
