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
        when (jassTable.currentTrumpf!!.mode) {
            TrumpfMode.UNDEUFE -> {
                return playNextBestUndenufeCard(jassTable)
            }
            TrumpfMode.OBEABE -> {
                return playNextBestObenabeCard(jassTable)
            }
            TrumpfMode.TRUMPF -> {
                return playRandomCard(jassTable)
            }
            else -> {
                return playRandomCard(jassTable)
            }
        }
    }

    fun playNotFirstCardOfStich(jassTable: JassTable): JassCard {
        when (jassTable.currentTrumpf?.mode) {
            TrumpfMode.UNDEUFE -> {
                return playNextWorstUndenufeCard(jassTable)
            }
            TrumpfMode.OBEABE -> {
                return playNextWorstObenabeCard(jassTable)
            }
            TrumpfMode.TRUMPF -> {
                return playRandomCard(jassTable)
            }
            else -> {
                return playRandomCard(jassTable)
            }
        }
    }

    fun playNextBestUndenufeCard(jassTable: JassTable): JassCard {
        val cardsSortedAbsolutely = jassTable.myCards.sortBy { it.number }
        return cardsSortedAbsolutely.first()
    }

    fun playNextBestObenabeCard(jassTable: JassTable): JassCard {
        val cardsSortedAbsolutely = jassTable.myCards.sortBy { it.number }
        return cardsSortedAbsolutely.last()
    }


    fun playNextWorstUndenufeCard(jassTable: JassTable): JassCard {
        val cardsSortedAbsolutely = jassTable.myCards.sortBy { it.number }

        val firstCard = jassTable.cardsOnTable.get(0);
        val filteredByFirstCardsColor = cardsSortedAbsolutely.filter { it.color == firstCard.color }
        if (!filteredByFirstCardsColor.isEmpty()) {
            return filteredByFirstCardsColor.last()
        } else {
            return cardsSortedAbsolutely.last()
        }
    }

    fun playNextWorstObenabeCard(jassTable: JassTable): JassCard {
        val cardsSortedAbsolutely = jassTable.myCards.sortBy { it.number }

        val firstCard = jassTable.cardsOnTable.get(0);
        val filteredByFirstCardsColor = cardsSortedAbsolutely.filter { it.color == firstCard.color }
        if (!filteredByFirstCardsColor.isEmpty()) {
            return filteredByFirstCardsColor.first()
        } else {
            return cardsSortedAbsolutely.first()
        }
    }


    fun playRandomCard(jassTable: JassTable): JassCard {
        if (jassTable.myCards.size() == 0) {
            throw IllegalStateException("no more cards to play")
        }

        val playedCardIndex = random.nextInt(jassTable.myCards.size())
        return jassTable.myCards.get(playedCardIndex)

    }


}

