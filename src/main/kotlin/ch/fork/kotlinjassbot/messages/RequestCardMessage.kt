package ch.fork.kotlinjassbot.messages

import ch.fork.kotlinjassbot.model.JassCard

/**
 * Created by fork on 08.07.15.
 */
public data class RequestCardMessage(val cards : Array<JassCard>)