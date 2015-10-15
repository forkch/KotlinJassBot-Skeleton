package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.model.CardColor
import ch.fork.kotlinjassbot.model.JassCard
import java.util.*

/**
 * Created by fork on 09.07.15.
 */
open  class LogicBaseTest {


    fun ArrayList<JassCard>.addJassCard(number: Int, color: CardColor): ArrayList<JassCard> {
        this.add(JassCard(number, color))
        return this;
    }

    fun ArrayList<JassCard>.addSpades(number: Int): ArrayList<JassCard> {
        this.add(JassCard(number, CardColor.SPADES))
        return this;
    }
    fun ArrayList<JassCard>.addClubs(number: Int): ArrayList<JassCard> {
        this.add(JassCard(number, CardColor.CLUBS))
        return this;
    }
    fun ArrayList<JassCard>.addHearts(number: Int): ArrayList<JassCard> {
        this.add(JassCard(number, CardColor.HEARTS))
        return this;
    }
    fun ArrayList<JassCard>.addDiamonds(number: Int): ArrayList<JassCard> {
        this.add(JassCard(number, CardColor.DIAMONDS))
        return this;
    }

}