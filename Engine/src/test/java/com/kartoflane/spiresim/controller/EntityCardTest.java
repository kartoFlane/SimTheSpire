package com.kartoflane.spiresim.controller;

import com.kartoflane.spiresim.state.StateFactory;
import com.kartoflane.spiresim.state.card.CardState;
import com.kartoflane.spiresim.state.entity.CardPileType;
import com.kartoflane.spiresim.state.entity.EntityState;
import com.kartoflane.spiresim.test.controller.TestGameController;
import com.kartoflane.spiresim.test.template.card.TestCardTemplate;
import com.kartoflane.spiresim.test.template.entity.TestEntityTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EntityCardTest {
    private GameController gameController;
    private EntityController controller;
    private EntityState state;


    @Before
    public void prepareMockEntities() {
        gameController = TestGameController.setupTestEnvironment(TestEntityTemplate.getInstance());

        controller = gameController.getPlayerController();
        state = gameController.getPlayerController().getState();
    }

    @Test
    public void temporarilyAddedCardsShouldBeRemoved() {
        // Prepare
        CardState cardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        controller.addTemporaryCardToPileBottom(cardState, CardPileType.DRAW);

        // Execute
        controller.resetAndShuffleDecks(gameController);

        // Check
        assertFalse(state.getAllCards().contains(cardState));
        assertTrue(state.getTemporaryCardPile().isEmpty());
        assertNull(controller.getCardController(cardState));
    }

    @Test
    public void permanentlyAddedCardsShouldNotBeRemoved() {
        // Prepare
        CardState cardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        controller.addPermanentCardToPileBottom(cardState, CardPileType.DRAW);

        // Execute
        controller.resetAndShuffleDecks(gameController);

        // Check
        assertTrue(state.getAllCards().contains(cardState));
        assertTrue(state.getTemporaryCardPile().isEmpty());
        assertNotNull(controller.getCardController(cardState));
    }

    @Test
    public void afterResetAndShuffleAllCardsShouldBeInDrawPile() {
        // Prepare
        for (CardPileType cardPileType : CardPileType.values()) {
            addCardsToPile(cardPileType, 3);
        }
        final int expectedCardCount = state.getAllCards().size();

        // Execute
        controller.resetAndShuffleDecks(gameController);

        // Check
        assertEquals(expectedCardCount, state.getDrawPileList().size());
        assertTrue(state.getHandList().isEmpty());
        assertTrue(state.getDiscardPileList().isEmpty());
        assertTrue(state.getExhaustPileList().isEmpty());
        assertTrue(state.getUsedPowersList().isEmpty());
        assertTrue(state.getTemporaryCardPile().isEmpty());
    }

    @Test
    public void drawingACardWhenDrawAndDiscardPilesAreEmptyShouldReturnNull() {
        // Prepare
        assertTrue(state.getDrawPileList().isEmpty());
        assertTrue(state.getDiscardPileList().isEmpty());

        // Execute
        CardState drawnCard = controller.drawCard(gameController);

        // Check
        assertNull(drawnCard);
    }

    @Test
    public void drawingACardShouldRemoveItFromDrawPile() {
        // Prepare
        addCardsToPile(CardPileType.DRAW, 3);

        // Execute
        CardState drawnCard = controller.drawCard(gameController);

        // Check
        assertFalse(state.getDrawPileList().contains(drawnCard));
    }

    @Test
    public void drawingACardShouldDrawFirstCardFromDrawPile() {
        // Prepare
        final CardState expectedCardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        controller.addPermanentCardToPileBottom(expectedCardState, CardPileType.DRAW);

        addCardsToPile(CardPileType.DRAW, 2);

        // Execute
        CardState drawnCard = controller.drawCard(gameController);

        // Check
        assertEquals(expectedCardState, drawnCard);
    }

    @Test
    public void drawingWhenDrawPileIsEmptyShouldShuffleDiscardPile() {
        // Prepare
        addCardsToPile(CardPileType.DISCARD, 3);
        assertTrue(state.getDrawPileList().isEmpty());
        assertFalse(state.getDiscardPileList().isEmpty());

        // Execute
        controller.drawCard(gameController);

        // Check
        assertFalse(state.getDrawPileList().isEmpty());
        assertTrue(state.getDiscardPileList().isEmpty());
    }

    @Test
    public void drawingAHandShouldMoveCardsToHand() {
        // Prepare
        final int drawPileSize = 5;
        final int cardsToDraw = 3;
        final int expectedCardsInDrawPile = drawPileSize - cardsToDraw;

        addCardsToPile(CardPileType.DRAW, drawPileSize);

        assertFalse(state.getDrawPileList().isEmpty());
        assertTrue(state.getHandList().isEmpty());

        // Execute
        controller.drawHand(gameController, cardsToDraw);

        // Check
        assertEquals(expectedCardsInDrawPile, state.getDrawPileList().size());
        assertEquals(cardsToDraw, state.getHandList().size());
    }

    @Test
    public void discardingHandShouldMoveCardsToDiscardPile() {
        // Prepare
        addCardsToPile(CardPileType.HAND, 3);

        assertTrue(state.getDiscardPileList().isEmpty());
        assertFalse(state.getHandList().isEmpty());

        // Execute
        controller.discardHand(gameController);

        // Check
        assertFalse(state.getDiscardPileList().isEmpty());
        assertTrue(state.getHandList().isEmpty());
    }

    @Test
    public void cardShouldBePlayableWhenSufficientEnergy() {
        // Prepare
        final CardState cardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        cardState.setCost(state.getEnergyCurrent() - 1);

        // Execute
        boolean isPlayable = controller.isCardPlayable(cardState);

        // Check
        assertTrue(isPlayable);
    }

    @Test
    public void cardShouldNotBePlayableWhenInsufficientEnergy() {
        // Prepare
        final CardState cardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        cardState.setCost(state.getEnergyCurrent() + 1);

        // Execute
        boolean isPlayable = controller.isCardPlayable(cardState);

        // Check
        assertFalse(isPlayable);
    }

    @Test
    public void playableCardsShouldReturnCardsWithSufficientEnergy() {
        // Prepare
        final int expectedPlayableCards = 3;

        final CardState expensiveCardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
        expensiveCardState.setCost(state.getEnergyCurrent() + 1);
        controller.addPermanentCardToPileBottom(expensiveCardState, CardPileType.HAND);
        addCardsToPile(CardPileType.HAND, expectedPlayableCards);

        // Execute
        List<CardState> playableCards = controller.getPlayableCards();

        // Check
        assertEquals(expectedPlayableCards, playableCards.size());
        assertFalse(playableCards.contains(expensiveCardState));
    }

    private void addCardsToPile(CardPileType pileType, int cardCount) {
        for (int i = 0; i < cardCount; ++i) {
            CardState cardState = StateFactory.build(gameController, TestCardTemplate.getInstance());
            controller.addPermanentCardToPileBottom(cardState, pileType);
        }
    }
}
