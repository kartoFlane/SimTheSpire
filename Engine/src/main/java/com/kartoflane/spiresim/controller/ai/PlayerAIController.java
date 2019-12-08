package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingResult;
import com.kartoflane.spiresim.state.CardState;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerAIController implements AIController {

    @Override
    public EntityController getEnemyTargetSingle(GameController gameController, EncounterController encounterController) {
        Random random = gameController.getState().getRandom();

        int index = random.nextInt(encounterController.listEnemyControllers().size());
        return encounterController.listEnemyControllers().get(index);
    }

    @Override
    public List<EntityController> getEnemyTargetsAll(GameController gameController, EncounterController encounterController) {
        return encounterController.listEnemyControllers();
    }

    @Override
    public void controlEntity(GameController gameController, EncounterController encounterController, EntityController entity) {
        entity.drawHand(gameController.getState(), encounterController, 5);

        List<CardState> playableCards;
        do {
            playableCards = entity.getPlayableCards();
            Collections.shuffle(playableCards, gameController.getState().getRandom());
            processPlayableCards(gameController, encounterController, entity, playableCards);
        } while (!playableCards.isEmpty());
    }

    private void processPlayableCards(GameController game, EncounterController encounter, EntityController caster, List<CardState> playableCards) {
        for (CardState playableCard : playableCards) {
            CardController<?, ?> card = caster.getCardController(playableCard);
            boolean wasCardPlayed = processCard(game, encounter, caster, card);
            if (wasCardPlayed) {
                break;
            }
        }
    }

    private boolean processCard(GameController game, EncounterController encounter, EntityController caster, CardController<?, ?> card) {
        TargetingResult targetingResult = card.getTargetingController().selectTargets(game, encounter, this);
        if (targetingResult.getType().isValidTarget()) {
            caster.playCard(encounter, card, targetingResult.getTargets());
            return true;
        }

        return false;
    }
}
