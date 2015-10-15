package ch.fork.kotlinjassbot.messages

import ch.fork.kotlinjassbot.model.TeamScore

/**
 * Created by fork on 08.07.15.
 */
public data class BroadcastGameFinishedMessage(val teams : Array<TeamScore>)