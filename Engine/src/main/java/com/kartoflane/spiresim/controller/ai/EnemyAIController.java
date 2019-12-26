package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingResult;
import com.kartoflane.spiresim.state.CardState;

import java.util.Collections;
import java.util.List;

public class EnemyAIController implements AIController {

    @Override
    public EntityController getEnemyTargetSingle(GameController gameController) {
        return gameController.getPlayerController();
    }

    @Override
    public List<EntityController> getEnemyTargetsAll(GameController gameController) {
        return Collections.singletonList(gameController.getPlayerController());
    }

    @Override
    public void controlEntity(GameController gameController, EntityController entity) {
        entity.drawHand(gameController, 1);

        List<CardState> playableCards = entity.getPlayableCards();
        processPlayableCards(gameController, entity, playableCards);
    }

    private void processPlayableCards(GameController game, EntityController caster, List<CardState> playableCards) {
        for (CardState playableCard : playableCards) {
            CardController<?, ?> card = caster.getCardController(playableCard);
            boolean wasCardPlayed = processCard(game, caster, card);
            if (wasCardPlayed) {
                break;
            }
        }
    }

    private boolean processCard(GameController gameController, EntityController caster, CardController<?, ?> card) {
        TargetingResult targetingResult = card.getTargetingController().selectTargets(gameController, this);
        if (targetingResult.getType().isValidTarget()) {
            caster.playCard(gameController, card, targetingResult.getTargets());
            return true;
        }

        return false;
    }
}
