package com.smahama.api.game.helper;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Helper class to "shuffle" a list
 *
 *
 */
@Component
public class ListShuffleHelper {

    private static final Random RANDOM = new Random();

    private static final Logger LOGGER = LoggerFactory.getLogger(ListShuffleHelper.class);

    /**
     * Defeult constructor
     */
    public ListShuffleHelper() {

        super();
    }

    /**
     * Method to shuffle the list
     * It swaps the current index by another using a random index
     * @param <T> the object into list
     * @param list the list of type T
     */
    public <T> void shuffle(
        final List<T> list) {

        LOGGER.debug("BEFORE: {}", list);

        if (list != null && !list.isEmpty()) {
            final int size = list.size();

            for (int i = 0; i < size; i++) {
                final int newIndex = i + RANDOM.nextInt(size - i);
                swap(list, i, newIndex);
            }
        }
        LOGGER.debug("AFTER: {}", list);

    }

    private <T> void swap(
        final List<T> list,
        final int index,
        final int value) {

        final T objectContent = list.get(index);
        list.set(index, list.get(value));
        list.set(value, objectContent);
    }
}
