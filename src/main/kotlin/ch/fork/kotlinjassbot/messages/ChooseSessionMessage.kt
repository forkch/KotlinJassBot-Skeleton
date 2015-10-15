package ch.fork.kotlinjassbot.messages

import ch.fork.kotlinjassbot.model.SessionChoice

/**
 * Created by fork on 08.07.15.
 */
public data class ChooseSessionMessage(val sessionChoice : SessionChoice, val sessionName : String)

