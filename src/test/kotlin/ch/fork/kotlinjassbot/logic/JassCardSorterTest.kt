package ch.fork.kotlinjassbot.logic

import ch.fork.kotlinjassbot.model.CardColor
import ch.fork.kotlinjassbot.model.JassCard
import org.junit.Test
import java.util.*

import  org.assertj.core.api.Assertions.assertThat

/**
 * Created by fork on 09.07.15.
 */

public class JassCarterSorterTest {
    @Test fun testSortJassCardsByValue() {
        // given
        val testJassCards = ArrayList<JassCard>()
        testJassCards.add(JassCard(8, CardColor.CLUBS))
        testJassCards.add(JassCard(6, CardColor.CLUBS))
        testJassCards.add(JassCard(7, CardColor.CLUBS))
        testJassCards.add(JassCard(14, CardColor.CLUBS))
        testJassCards.add(JassCard(13, CardColor.CLUBS))
        testJassCards.add(JassCard(10, CardColor.CLUBS))

        // when
        val sortedJassCards = sortJassCards(testJassCards)

        // then
        assertThat(sortedJassCards.clubs.get(0).number).isEqualTo(6);
        assertThat(sortedJassCards.clubs.get(1).number).isEqualTo(7);
        assertThat(sortedJassCards.clubs.get(2).number).isEqualTo(8);
        assertThat(sortedJassCards.clubs.get(3).number).isEqualTo(10);
        assertThat(sortedJassCards.clubs.get(4).number).isEqualTo(13);
        assertThat(sortedJassCards.clubs.get(5).number).isEqualTo(14);

    }

    @Test fun testSortJassCardsByValueAndColor() {
        // given
        val testJassCards = ArrayList<JassCard>()
        testJassCards.add(JassCard(9, CardColor.CLUBS))
        testJassCards.add(JassCard(14, CardColor.DIAMONDS))
        testJassCards.add(JassCard(8, CardColor.CLUBS))
        testJassCards.add(JassCard(6, CardColor.SPADES))
        testJassCards.add(JassCard(13, CardColor.CLUBS))
        testJassCards.add(JassCard(11, CardColor.HEARTS))
        testJassCards.add(JassCard(10, CardColor.HEARTS))
        testJassCards.add(JassCard(6, CardColor.HEARTS))
        testJassCards.add(JassCard(9, CardColor.HEARTS))
        assertThat(testJassCards).hasSize(9)

        // when
        val sortedJassCards = sortJassCards(testJassCards)

        // then
        assertThat(sortedJassCards.clubs).hasSize(3)
        assertThat(sortedJassCards.spades).hasSize(1)
        assertThat(sortedJassCards.diamonds).hasSize(1)
        assertThat(sortedJassCards.hearts).hasSize(4)

        assertThat(sortedJassCards.clubs.get(0).number).isEqualTo(8);
        assertThat(sortedJassCards.clubs.get(1).number).isEqualTo(9);
        assertThat(sortedJassCards.clubs.get(2).number).isEqualTo(13);

        assertThat(sortedJassCards.spades.get(0).number).isEqualTo(6);
        assertThat(sortedJassCards.diamonds.get(0).number).isEqualTo(14);

        assertThat(sortedJassCards.hearts.get(0).number).isEqualTo(6);
        assertThat(sortedJassCards.hearts.get(1).number).isEqualTo(9);
        assertThat(sortedJassCards.hearts.get(2).number).isEqualTo(10);
        assertThat(sortedJassCards.hearts.get(3).number).isEqualTo(11);


    }}
