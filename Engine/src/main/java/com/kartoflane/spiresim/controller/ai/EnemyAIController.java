package com.kartoflane.spiresim.controller.ai;

import com.kartoflane.spiresim.controller.CardController;
import com.kartoflane.spiresim.controller.EncounterController;
import com.kartoflane.spiresim.controller.EntityController;
import com.kartoflane.spiresim.controller.GameController;
import com.kartoflane.spiresim.controller.targeting.TargetingResult;
import com.kartoflane.spiresim.state.CardState;

import java.util.Collections;
import java.util.List;

public class EnemyAIController implements AIController {

    @Override
    public EntityController getEnemyTargetSingle(GameController gameController, EncounterController encounterController) {
        return gameController.getPlayerController();
    }

    @Override
    public List<EntityController> getEnemyTargetsAll(GameController gameController, EncounterController encounterController) {
        return Collections.singletonList(gameController.getPlayerController());
    }

    @Override
    public void controlEntity(GameController gameController, EncounterController encounterController, EntityController entity) {
        entity.drawHand(gameController.getState(), encounterController, 1);

        List<CardState> playableCards = entity.getPlayableCards();
        processPlayableCards(gameController, encounterController, entity, playableCards);
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
