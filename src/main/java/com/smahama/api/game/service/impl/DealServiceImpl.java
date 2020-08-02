package com.smahama.api.game.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smahama.api.game.dto.PlayerCards;
import com.smahama.api.game.exception.GameNotFoundException;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Deal;
import com.smahama.api.game.repository.DealRepository;
import com.smahama.api.game.repository.GameRepository;
import com.smahama.api.game.service.DealService;

@Service
public class DealServiceImpl
    implements DealService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DealRepository dealRepository;

    private DealServiceImpl() {

        //
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Deal dealCards(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var player =
            game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getPlayerId().equals(playerId)).findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        final var dealCard = this.gameRepository.getNextCard(game);

        if (dealCard != null) {

            return this.dealRepository.createDeal(gameId, player.getPlayerId(), dealCard);
        }

        return null;
    }

    @Override
    public PlayerCards getPlayerCards(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var player =
            game.getPlayers().stream().filter(gamePlayer -> gamePlayer.getPlayerId().equals(playerId)).findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        final var cards =
            this.dealRepository.getAllDeals().stream().filter(
                deal -> deal.getGameId().equals(game.getGameId()) && deal.getPlayerId().equals(player.getPlayerId()))
                .map(Deal::getCard).collect(Collectors.toList());

        return new PlayerCards(playerId, cards);
    }

    @Override
    public List<PlayerCards> getPlayersCards(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = this.gameRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final var playersCards = new ArrayList<PlayerCards>();
        for (final var player : game.getPlayers()) {

            final var cards =
                this.dealRepository.getAllDeals().stream()
                    .filter(deal -> deal.getGameId().equals(game.getGameId())
                        && deal.getPlayerId().equals(player.getPlayerId()))
                    .map(Deal::getCard).collect(Collectors.toList());

            playersCards.add(new PlayerCards(player.getPlayerId(), cards));
        }

        return playersCards;
    }
}
