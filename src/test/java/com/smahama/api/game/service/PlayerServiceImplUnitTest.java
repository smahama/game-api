package com.smahama.api.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smahama.api.game.dto.CreatePlayerRequest;
import com.smahama.api.game.exception.PlayerNotFoundException;
import com.smahama.api.game.model.Player;
import com.smahama.api.game.repository.PlayerRepository;
import com.smahama.api.game.service.impl.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplUnitTest {

    private static final Integer PLAYER_ID = 1;

    private static final String PLAYER_NAME = "PLAYER NAME";

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private Player player;

    public PlayerServiceImplUnitTest() {

        //
    }

    @Test
    public void testCreatePlayer() {

        // given
        final var createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setPlayerName(PLAYER_NAME);
        when(this.playerRepository.createPlayer(anyString())).thenReturn(this.player);

        // when
        final var newPlayer = this.playerService.createPlayer(createPlayerRequest);

        // then
        assertEquals(this.player, newPlayer);
        verify(this.playerRepository).createPlayer(eq(PLAYER_NAME));
    }

    @Test
    public void testGetPlayer()
        throws PlayerNotFoundException {

        // given
        when(this.playerRepository.getPlayer(anyInt())).thenReturn(Optional.of(this.player));

        // when
        final var getPlayer = this.playerService.getPlayer(PLAYER_ID);

        // then
        verify(this.playerRepository).getPlayer(eq(PLAYER_ID));
        assertEquals(this.player, getPlayer);
    }

    @Test
    public void testGetNotFoundPlayer() {

        // given
        when(this.playerRepository.getPlayer(anyInt())).thenReturn(Optional.empty());

        // when && then
        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayer(PLAYER_ID));

        // then
        verify(this.playerRepository).getPlayer(eq(PLAYER_ID));
    }
}
