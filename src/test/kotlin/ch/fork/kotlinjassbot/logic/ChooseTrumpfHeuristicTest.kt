package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.model.CardColor
import ch.fork.kotlinjassbot.model.JassCard
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*
import  org.assertj.core.api.Assertions.assertThat

import ch.fork.kotlinjassbot.logic.*


/**
 * Created by fork on 09.07.15.
 */
public class ChooseTrumpfHeuristicTest : LogicBaseTest() {

    @Test fun testTrumpfPoints() {
        // given
        val testJassCards = ArrayList<JassCard>()

                .addClubs(9)
                .addClubs(11)
                .addClubs(12)
                .addClubs(13)
                .addClubs(14)
                .addClubs(6)
                .addClubs(8)
                .addClubs(7)
                .addClubs(6)

        // when
        val points = getTrumpfPoints(testJassCards)

        // then
        assertThat(points).isEqualTo(46)
    }

    @Test fun testUndeufePointsNo6() {
        // given
        val testJassCards = ArrayList<JassCard>()
                .addClubs(7)
                .addClubs(8)
                .addClubs(9)
                .addClubs(10)
                .addClubs(11)
                .addClubs(12)


        // when
        val points = getUndeufePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(0)
    }

    @Test fun testUndeufePointsNoCard() {
        // given
        val testJassCards = ArrayList<JassCard>()

        // when
        val points = getUndeufePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(0)
    }

    @Test fun testUndeufePointsWith6FourConsecutive() {
        // given
        val testJassCards = ArrayList<JassCard>()
                .addClubs(6)
                .addClubs(7)
                .addClubs(8)
                .addClubs(9)


        // when
        val points = getUndeufePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(4)
    }


    @Test fun testObenabePointsWithoutAce() {
        // given
        val testJassCards = ArrayList<JassCard>()
                .addClubs(13)
                .addClubs(12)
                .addClubs(11)
                .addClubs(10)
                .addClubs(9)

        // when
        val points = getObenabePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(0)
    }

    @Test fun testObenabePointsNoCard() {
        // given
        val testJassCards = ArrayList<JassCard>()

        // when
        val points = getObenabePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(0)
    }

    @Test fun testObenabePointsWithAceFourConsecutive() {
        // given
        val testJassCards = ArrayList<JassCard>()
                .addClubs(14)
                .addClubs(13)
                .addClubs(12)
                .addClubs(11)

        // when
        val points = getObenabePoints(testJassCards)

        // then
        assertThat(points).isEqualTo(4)
    }

}