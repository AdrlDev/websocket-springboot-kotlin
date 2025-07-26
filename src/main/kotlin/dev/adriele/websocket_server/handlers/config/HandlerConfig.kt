package dev.adriele.websocket_server.handlers.config

import dev.adriele.websocket_server.handlers.InsightWebSocketHandler
import dev.adriele.websocket_server.handlers.TipsWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class HandlerConfig {
    @Bean
    fun webClient(): WebClient = WebClient.create()

    @Bean
    fun tipsWebSocketHandler(webClient: WebClient) = TipsWebSocketHandler(webClient)

    @Bean
    fun insightWebSocketHandler(webClient: WebClient) = InsightWebSocketHandler(webClient)
}