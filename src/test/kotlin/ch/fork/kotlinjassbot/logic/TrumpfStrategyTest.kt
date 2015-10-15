package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.model.JassCard
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by fork on 09.07.15.
 */
public class TrumpfStrategyTest() : LogicBaseTest() {


    @Test public fun testChooseTrumpfHearts() {
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
        Assertions.assertThat(points).isEqualTo(46)
    }

}