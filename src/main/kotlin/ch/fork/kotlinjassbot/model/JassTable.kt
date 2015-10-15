package ch.fork.kotlinjassbot.model

import ch.fork.kotlinjassbot.messages.RequestCardMessage
import java.util.*

/**
 * Created by fork on 10.07.15.
 */
public class JassTable {
    var iChoseTrumpf: Boolean = false

    var cardsOnTable: ArrayList<JassCard> = ArrayList()
    var myCards: ArrayList<JassCard> = ArrayList()

    var lastPlayedCard: JassCard? = null
    var currentTrumpf: Trumpf? = null
    var gschobe: Boolean = false;

    public fun resetAfterGameCompleted() {
        currentTrumpf = null
        gschobe = false
    }

    public fun resetAfterStichCompleted() {
        cardsOnTable = ArrayList<JassCard>()
        removeLastPlayedCardFromMyCards()
    }

    public fun addMyCards(cards: Array<JassCard>) {
        myCards.clear()
        myCards.addAll(cards);
    }

    fun removeLastPlayedCardFromMyCards() {
        myCards.remove(lastPlayedCard)
    }

    fun canIGiveFirstCardOfStich(): Boolean = cardsOnTable.isEmpty()

    fun getAllowedCardsSorted(): List<JassCard> {
        // todo this needs extension
        if (isNewStich())
            return myCards

        val allowedColor = getAllowedColor()

        val filteredByFirstCardsColor = myCards.filter { it.color == allowedColor } sortBy { it.number }
        if (filteredByFirstCardsColor.isEmpty()) {
            return myCards
        } else {
            return filteredByFirstCardsColor
        }
    }

    fun getAllowedColor(): CardColor? {
        if (cardsOnTable.isEmpty()) {
            return null;
        }
        return cardsOnTable.get(0).color;
    }

    fun isNewStich(): Boolean = cardsOnTable.isEmpty()

    fun getCurrentStateAsString(): String {
        return "\ncurrent trumpf $currentTrumpf\n" + "Cards on table: ${cardsOnTable.toString()}\n" + "my cards: $myCards\n" + "allowed cards: ${getAllowedCardsSorted()}"
    }
}
