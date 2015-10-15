package ch.fork.kotlinjassbot.model

/**
 * Created by fork on 08.07.15.
 */
data class Trumpf(val mode : TrumpfMode, val trumpfColor : CardColor?) {
    override fun toString(): String {
        when(mode) {
            TrumpfMode.UNDEUFE -> return "\u25B2"
            TrumpfMode.OBEABE -> return "\u25BC"
            TrumpfMode.TRUMPF -> return "$trumpfColor";
            TrumpfMode.SCHIEBE -> return "\u27A0"
        }
    }
}