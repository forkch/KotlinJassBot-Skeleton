package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.model.CardColor
import ch.fork.kotlinjassbot.model.JassCard

/**
 * Created by fork on 08.07.15.
 */

public data class SortedJassCards(val clubs:List<JassCard> ,val hearts:List<JassCard> ,val diamonds:List<JassCard>, val spades:List<JassCard>)


fun sortJassCards(myCards : List<JassCard>) :SortedJassCards {

    val sortedCards = myCards sortedBy { it.number }

    val clubs = sortedCards filter { it.color == CardColor.CLUBS }
    val hearts = sortedCards.filter { it.color == CardColor.HEARTS }
    val diamonds = sortedCards.filter { it.color == CardColor.DIAMONDS }
    val spades = sortedCards.filter { it.color == CardColor.SPADES }

    return SortedJassCards(clubs, hearts, diamonds, spades)

}


