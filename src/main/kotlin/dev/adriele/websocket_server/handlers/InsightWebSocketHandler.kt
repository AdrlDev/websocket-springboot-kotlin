package dev.adriele.websocket_server.handlers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.handler.TextWebSocketHandler
import reactor.core.publisher.Mono

class InsightWebSocketHandler(
    private val webClient: WebClient
) : TextWebSocketHandler() {

    private val objectMapper = ObjectMapper()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val rawJson = message.payload

        try {
            val bodyMap: Map<String, Any> = objectMapper.readValue(
                rawJson,
                object : TypeReference<Map<String, Any>>() {}
            )

            fetchInsights(bodyMap)
                .subscribe({ response ->
                    session.sendMessage(TextMessage(response))
                }, { error ->
                    session.sendMessage(TextMessage("⚠️ Error: ${error.localizedMessage}"))
                })

        } catch (e: Exception) {
            session.sendMessage(TextMessage("⚠️ Invalid JSON: ${e.localizedMessage}"))
        }
    }

    private fun fetchInsights(body: Map<String, Any>): Mono<String> {
        return webClient.post()
            .uri("https://adolescare-api.onrender.com/insights")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}
