package ch.fork.kotlinjassbot.messages

import ch.fork.kotlinjassbot.model.JassCard
import ch.fork.kotlinjassbot.model.TeamScore

/**
 * Created by fork on 08.07.15.
 */
public data class BroadcastStichMessage(val name : String, id : Int,  val playedCards : Array<JassCard>, val teams : Array<TeamScore>)