package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.JassServerWebsocketClient
import ch.fork.kotlinjassbot.messages.MessageType
import ch.fork.kotlinjassbot.messages.RequestCardMessage
import ch.fork.kotlinjassbot.model.JassCard
import ch.fork.kotlinjassbot.model.JassTable
import ch.fork.kotlinjassbot.model.Trumpf
import ch.fork.kotlinjassbot.model.TrumpfMode
import java.util.*

/**
 * Created by fork on 09.07.15.
 */

var random = Random()

public class RequestCardStrategy {
    fun playCard(jassTable: JassTable): JassCard =
            if (jassTable.canIGiveFirstCardOfStich()) {
                playFirstCardOfStich(jassTable);
            } else {
                playNotFirstCardOfStich(jassTable)
            }


    fun playFirstCardOfStich(jassTable: JassTable): JassCard {
        return playRandomCard(jassTable)
    }

    fun playNotFirstCardOfStich(jassTable: JassTable): JassCard {
        return playRandomCard(jassTable)
    }

    fun playRandomCard(jassTable: JassTable): JassCard {
        if (jassTable.myCards.size() == 0) {
            throw IllegalStateException("no more cards to play")
        }

        val playedCardIndex = random.nextInt(jassTable.myCards.size())
        return jassTable.myCards.get(playedCardIndex)

    }


}

