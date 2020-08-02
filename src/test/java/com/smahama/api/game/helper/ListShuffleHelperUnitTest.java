package com.smahama.api.game.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for ListShuffleHelper class
 *
 */
@SpringBootTest
public class ListShuffleHelperUnitTest {

    @Autowired
    private ListShuffleHelper listShuffleHelper;

    public ListShuffleHelperUnitTest() {

        super();
    }

    @Test
    public void givenListWhenShuffleThenListIsShuffled() {

        final List<Integer> listOriginal = new ArrayList<>();
        listOriginal.add(1);
        listOriginal.add(2);
        listOriginal.add(3);
        listOriginal.add(4);

        final List<Integer> list = new ArrayList<>(listOriginal);

        this.listShuffleHelper.shuffle(list);

        assertNotEquals(listOriginal, list);
        assertEquals(listOriginal.size(), list.size());
        assertTrue(listOriginal.containsAll(list));

    }
}
