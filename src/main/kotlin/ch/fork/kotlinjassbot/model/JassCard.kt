package ch.fork.kotlinjassbot.model

/**
 * Created by fork on 07.07.15.
 */


data class JassCard(val number: Int, val color: CardColor) {

    override fun toString(): String {

        var string = "";
        if (isAce()) string += "Ass "
        else if (isKoenig()) string += "König "
        else if (isDame()) string += "Dame "
        else if (isBuur()) string += "Buur "
        else string += "" + number + " "

        when (color) {
            CardColor.CLUBS -> string += '\u2663'
            CardColor.DIAMONDS -> string += '\u2666'
            CardColor.SPADES -> string += '\u2660'
            CardColor.HEARTS -> string += '\u2665'
        }
        return string;
    }

    fun isNaell(): Boolean {
        return number == 9
    }

    fun isBuur(): Boolean {
        return number == 11
    }

    fun isDame(): Boolean {
        return number == 12
    }

    fun isKoenig(): Boolean {
        return number == 13
    }

    fun isAce(): Boolean {
        return number == 14
    }

    fun getNumberForTrumpfComparison(): Int = when {
        isNaell() -> 15
        isBuur() -> 16
        else -> number
    }

    fun beatsOtherCard(otherCard: JassCard, trumpf: Trumpf): Boolean {
        return when (trumpf.mode) {
            TrumpfMode.OBEABE -> compareCardObenabe(otherCard)
            TrumpfMode.UNDEUFE -> compareCardUndeufe(otherCard)
            TrumpfMode.TRUMPF -> compareCardTrumpf(otherCard, trumpf.trumpfColor!!)
            else -> throw  IllegalStateException("Trumpf mode unknown")
        }
    }

    private fun compareCardTrumpf(otherCard: JassCard, trumpfColor: CardColor): Boolean {
        if (color == trumpfColor) {
            if (otherCard.color == trumpfColor) {
                return getNumberForTrumpfComparison().compareTo(otherCard.getNumberForTrumpfComparison()) > 0
            } else {
                return true
            }
        } else {
            if (otherCard.color == trumpfColor) {
                return false
            } else {
                return number.compareTo(otherCard.number) > 0
            }
        }
    }

    private fun compareCardUndeufe(otherCard: JassCard): Boolean = number.compareTo(otherCard.number) < 0

    fun compareCardObenabe(otherCard: JassCard): Boolean = number.compareTo(otherCard.number) > 0
}