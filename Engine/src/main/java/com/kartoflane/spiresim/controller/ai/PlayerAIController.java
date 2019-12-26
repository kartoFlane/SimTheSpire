package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingResult;
import com.kartoflane.spiresim.state.CardState;
import com.kartoflane.spiresim.util.RandomExt;

import java.util.Collections;
import java.util.List;

public class PlayerAIController implements AIController {

    @Override
    public EntityController getEnemyTargetSingle(GameController gameController) {
        RandomExt random = gameController.getState().getRandom();
        List<EntityController> enemyControllers = gameController.getCurrentEncounter().listEnemyControllers();

        int index = random.nextInt(enemyControllers.size());
        return enemyControllers.get(index);
    }

    @Override
    public List<EntityController> getEnemyTargetsAll(GameController gameController) {
        return gameController.getCurrentEncounter().listEnemyControllers();
    }

    @Override
    public void controlEntity(GameController gameController, EntityController entity) {
        entity.drawHand(gameController, 5);

        List<CardState> playableCards;
        do {
            playableCards = entity.getPlayableCards();
            Collections.shuffle(playableCards, gameController.getState().getRandom());
            processPlayableCards(gameController, entity, playableCards);
        } while (!playableCards.isEmpty());
    }

    private void processPlayableCards(GameController gameController, EntityController caster, List<CardState> playableCards) {
        for (CardState playableCard : playableCards) {
            CardController<?, ?> card = caster.getCardController(playableCard);
            boolean wasCardPlayed = processCard(gameController, caster, card);
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
