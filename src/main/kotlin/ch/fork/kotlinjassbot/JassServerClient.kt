package ch.fork.kotlinjassbot

import ch.fork.kotlinjassbot.messages.ConnectedEvent
import ch.fork.kotlinjassbot.messages.MessageType
import ch.fork.kotlinjassbot.model.*
import com.google.common.eventbus.EventBus
import org.apache.log4j.Logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.properties.Delegates

/**
 * Created by fork on 06.07.15.
 */


class JassServerWebsocketClient(val bus: EventBus, val name: String, val url : String){

    val LOG : Logger = Logger.getLogger(JassServerWebsocketClient::class.java)


    var client : WebSocketClient by Delegates.notNull()
    init {
        client = object : WebSocketClient(URI(url)) {
            override fun onOpen(handshakedata: ServerHandshake) {
                d("handshakedata = [" + handshakedata + "]")
                bus.post(ConnectedEvent())
            }

            override fun onMessage(message: String) {
                d("onMessage $message")

                val event = mapServerMessage(message)
                bus.post(event)
            }

            override fun onClose(code: Int, reason: String, remote: Boolean) {
                d("code = [" + code + "], reason = [" + reason + "], remote = [" + remote + "]")
            }

            override fun onError(ex: Exception) {
                LOG.error("ex = [" + ex + "]")
            }
        }
    }

    private fun d(msg: String) {
        LOG.debug("[$name] : $msg")
    }

    fun connect() {
        client.connect()
    }

    fun sendMessage(messageType : MessageType,  data : Any) {
        val clientJsonMessage = mapClientMessage(messageType, data)
        client.send(clientJsonMessage)
        LOG.debug("sent message $clientJsonMessage")
    }

}