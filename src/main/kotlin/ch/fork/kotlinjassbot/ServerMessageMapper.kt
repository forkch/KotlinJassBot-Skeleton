package ch.fork.kotlinjassbot

import ch.fork.kotlinjassbot.messages.*
import ch.fork.kotlinjassbot.model.*
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.apache.log4j.Logger

/**
 * Created by fork on 08.07.15.
 */



val gson = Gson()
var jsonParser = JsonParser()

var KEY_TYPE = "type"
var KEY_DATA = "data"


val LOG : Logger = Logger.getLogger("ServerMessageMapper")

fun mapServerMessage(message : String) : Any {

    var messageAsJsonObject = jsonParser.parse(message).getAsJsonObject()

    val messageTypeJson = messageAsJsonObject.get(KEY_TYPE)
    var messageType = gson.fromJson(messageTypeJson, MessageType::class.java)

    val data = messageAsJsonObject.get(KEY_DATA)
    LOG.debug("received message of type $messageType with data $data")


    when (messageType) {
        MessageType.REQUEST_PLAYER_NAME -> {
            return RequestPlayerNameMessage()
        }
        MessageType.REQUEST_SESSION_CHOICE -> {
            val sessions = gson.fromJson(data, Array<String>::class.java);
            return RequestSessionChoiceMessage(sessions)
        }
        MessageType.BROADCAST_SESSION_JOINED -> {
           return "";
        }
        MessageType.BROADCAST_TEAMS -> {
            val teams = gson.fromJson(data, Array<Team>::class.java)

            return Teams(teams);
        }
        MessageType.DEAL_CARDS -> {
            return DealCardsMessage(parseCardsArray(data))
        }
        MessageType.REQUEST_TRUMPF -> {
            val isGschobe = gson.fromJson(data, Boolean::class.java)
            return RequestTrumpfMessage(isGschobe)
        }
        MessageType.REJECT_TRUMPF -> {
            return "";
        }
        MessageType.BROADCAST_TRUMPF -> {
            val trumpf = gson.fromJson(data, Trumpf::class.java)
            return TrumpfChosenMessage(trumpf)
        }
        MessageType.REQUEST_CARD -> {
            return RequestCardMessage(parseCardsArray(data))
        }
        MessageType.REJECT_CARD -> {
            return RejectCardMessage(gson.fromJson(data, JassCard::class.java))
        }
        MessageType.PLAYED_CARDS -> {
            return PlayedCardsMessage(parseCardsArray(data))
        }
        MessageType.BROADCAST_STICH -> {
            return gson.fromJson(data, BroadcastStichMessage::class.java)
        }
        MessageType.BROADCAST_GAME_FINISHED -> {
            val teams = gson.fromJson(data, Array<TeamScore>::class.java);
            return BroadcastGameFinishedMessage(teams)
        }
        MessageType.BROADCAST_WINNER_TEAM -> {
            val winnerTeam = gson.fromJson(data, TeamScore::class.java);
            return BroadcastWinnerTeamMessage(winnerTeam)
        }
        MessageType.BAD_MESSAGE -> {
            return BadMessage(data.asString);
        }
        else -> {
            throw IllegalStateException("unknown message type $messageTypeJson")
        }
    }
}

private fun parseCardsArray(data: JsonElement?) = gson.fromJson(data, Array<JassCard>::class.java)

fun mapClientMessage(messageType : MessageType,  data : Any) : String {
    val message = JassMessage(messageType, data);
    return gson.toJson(message)
}