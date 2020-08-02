package com.smahama.api.game.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smahama.api.game.model.Card;
import com.smahama.api.game.model.Deal;

@Repository
public class DealRepository {

    private final List<Deal> deals = Collections.synchronizedList(new ArrayList<>());

    private DealRepository() {

        //
    }

    public Deal createDeal(
        final Integer gameId,
        final Integer playerId,
        final Card card) {

        final var deal = new Deal(playerId, gameId, card);
        this.deals.add(deal);

        return deal;
    }

    public List<Deal> getAllDeals() {

        return Collections.unmodifiableList(this.deals);
    }
}
