package dev.adriele.websocket_server.handlers

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.handler.TextWebSocketHandler
import reactor.core.publisher.Mono

class TipsWebSocketHandler(
    private val webClient: WebClient
) : TextWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val prompt = message.payload

        if(prompt.equals("todays-tips")) {
            fetchChatbotResponse()
                .subscribe { response ->
                    session.sendMessage(TextMessage(response))
                }
        }
    }

    private fun fetchChatbotResponse(): Mono<String> {
        return webClient.get()
            .uri("https://adolescare-api.onrender.com/todays-tip")
            .retrieve()
            .bodyToMono(String::class.java)
    }
}