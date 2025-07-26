package dev.adriele.websocket_server.handlers

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import reactor.core.publisher.Mono

@Component
class ChatWebSocketHandler : TextWebSocketHandler() {

    private val webClient = WebClient.builder()
        .baseUrl("https://adolescare-api.onrender.com")
        .build()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val userMessage = message.payload

        // Forward message to your chatbot API
        getChatbotResponse(userMessage)
            .subscribe({ response ->
                session.sendMessage(TextMessage(response))
            }, { error ->
                session.sendMessage(TextMessage("⚠️ Error: ${error.localizedMessage}"))
            })
    }

    private fun getChatbotResponse(input: String): Mono<String> {
        // Adjust endpoint/path if different
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/chat")
                    .queryParam("query", input)
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
    }
}