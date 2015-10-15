package ch.fork.kotlinjassbot

import ch.fork.kotlinjassbot.logic.*
import ch.fork.kotlinjassbot.messages.*
import ch.fork.kotlinjassbot.model.*
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.google.common.eventbus.SubscriberExceptionContext
import com.google.common.eventbus.SubscriberExceptionHandler
import org.apache.log4j.Logger
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by fork on 07.07.15.
 */
public class KotlinJassBot(val name: String, val sessionName: String, val url: String, val autoJoin: Boolean) {

    val LOG: Logger = Logger.getLogger(KotlinJassBot::class.java);
    val eventBus: EventBus = EventBus(object : SubscriberExceptionHandler {
        override fun handleException(p0: Throwable?, p1: SubscriberExceptionContext?) {
            LOG.error(p0);
        }
    });

    var client = JassServerWebsocketClient(eventBus, name, url)
    private val trumpfStrategy = TrumpfStrategy()
    private val requestCardStrategy = RequestCardStrategy()
    private val jassTable = JassTable()

    init {
        eventBus.register(this);
    }

    @Subscribe fun connected(event: ConnectedEvent) {
        LOG.i("Connected to Jass-Server")
    }

    @Subscribe fun requestPlayername(message: RequestPlayerNameMessage) {
        client.sendMessage(MessageType.CHOOSE_PLAYER_NAME, name)
    }

    @Subscribe fun requestSessionChoice(event: RequestSessionChoiceMessage) {
        LOG.i("available sessions " + Arrays.toString(event.data))
        if (autoJoin) {
            client.sendMessage(MessageType.CHOOSE_SESSION, ChooseSessionMessage(SessionChoice.AUTOJOIN, ""))
        } else {
            client.sendMessage(MessageType.CHOOSE_SESSION, ChooseSessionMessage(SessionChoice.JOIN_EXISTING, sessionName))
        }
    }

    @Subscribe fun dealCards(message: DealCardsMessage) {
        val cards = message.cards;

        jassTable.addMyCards(cards)
        LOG.d("received my cards ${jassTable.myCards}")
    }

    @Subscribe fun performTrumpf(message: RequestTrumpfMessage) {
        LOG.i("**********************************************************************************\n\n\n")
        var trumpfDecision: Trumpf
        LOG.i("Choosing Trumpf: ${jassTable.myCards}")
        if (message.isGschobe) {
            trumpfDecision = trumpfStrategy.chooseTrumpfGschobe(jassTable.myCards)
            LOG.i("gschoben trumpfDecision: $trumpfDecision")

        } else {
            trumpfDecision = trumpfStrategy.chooseTrumpf(jassTable.myCards)
            LOG.i("trumpfDecision: ${trumpfDecision}")
        }
        jassTable.iChoseTrumpf = true

        client.sendMessage(MessageType.CHOOSE_TRUMPF, trumpfDecision)
    }

    @Subscribe fun trumpfChosen(event: TrumpfChosenMessage) {
        if (event.trumpf.mode == TrumpfMode.SCHIEBE) {
            jassTable.gschobe = true
            LOG.d("GSCHOBE")
        } else {
            jassTable.gschobe = false
            jassTable.currentTrumpf = event.trumpf;
            LOG.d("${jassTable.currentTrumpf}")
        }
    }

    @Subscribe fun requestCard(event: RequestCardMessage) {
        jassTable.cardsOnTable = event.cards.toArrayList()
        var card = requestCardStrategy playCard jassTable
        jassTable.lastPlayedCard = card
        client.sendMessage(MessageType.CHOOSE_CARD, card);
    }

    @Subscribe fun cardRejected(event: RejectCardMessage) {
        LOG.e("card rejected ${event.card}\ncurrent JassTable ${jassTable.getCurrentStateAsString()}")
        var card = requestCardStrategy playCard jassTable
        jassTable.lastPlayedCard = card
        client.sendMessage(MessageType.CHOOSE_CARD, card);
    }

    @Subscribe fun stich(event: BroadcastStichMessage) {
        jassTable.resetAfterStichCompleted()
        LOG.i("remaining cards ${jassTable.myCards}")
    }

    @Subscribe fun gameFinished(event: BroadcastGameFinishedMessage) {
        jassTable.resetAfterGameCompleted()
        LOG.i("Game finished $event")
    }

    @Subscribe fun winner(event: BroadcastWinnerTeamMessage) {
        LOG.i("Winner $event")
    }

    @Subscribe fun badMessage(msg: BadMessage) = LOG.e("bad message {$msg.msg}")

    @Subscribe fun genericMessage(msg: String) = LOG.i("generic message: $msg")

    public fun start() {
        client.connect()
        LOG.i("KotlinJassBot $name started")
    }

    fun Logger.i(msg: String) {
        this.info("[$name] : $msg")
    }

    fun Logger.d(msg: String) {
        this.debug("[$name] : $msg")
    }

    fun Logger.e(msg: String) {
        this.error("[$name] : $msg")
    }

}


fun main(args: Array<String>) {
    val sessionName = "Kotlin Session " + UUID.randomUUID().toString();
    //    val url = "ws://jasschallenge.herokuapp.com"
    val url = "ws://localhost:3000"
    val autoJoin = true;
    var badassJassBot1 = KotlinJassBot("Kotlin Bot 1", sessionName, url, autoJoin)
    var badassJassBot2 = KotlinJassBot("Kotlin Bot 2", sessionName, url, autoJoin)
    var badassJassBot3 = KotlinJassBot("Kotlin Bot 3", sessionName, url, autoJoin)
    var badassJassBot4 = KotlinJassBot("Kotlin Bot 4", sessionName, url, autoJoin)

    badassJassBot1.start()
    badassJassBot2.start()
    badassJassBot3.start()
    badassJassBot4.start()
}
