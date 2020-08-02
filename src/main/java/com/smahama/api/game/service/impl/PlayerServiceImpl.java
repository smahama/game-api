package com.smahama.api.game.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smahama.api.game.dto.CreatePlayerRequest;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.repository.PlayerRepository;
import com.smahama.api.game.service.PlayerService;

/**
 * Class implementation of {@link PlayerService}
 * @author Salomon Mahama
 *
 */
@Service
public class PlayerServiceImpl
    implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    private PlayerServiceImpl() {

        //
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player createPlayer(
        final CreatePlayerRequest createPlayerRequest) {

        return this.playerRepository.createPlayer(createPlayerRequest.getPlayerName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer(
        final Integer playerId)
        throws PlayerNotFoundException {

        return this.playerRepository.getPlayer(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
