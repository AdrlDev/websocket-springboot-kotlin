package dev.adriele.websocket_server

import dev.adriele.websocket_server.handlers.ChatWebSocketHandler
import dev.adriele.websocket_server.handlers.InsightWebSocketHandler
import dev.adriele.websocket_server.handlers.TipsWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val chatHandler: ChatWebSocketHandler,
    private val tipsHandler: TipsWebSocketHandler,
    private val insightHandler: InsightWebSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*")
        registry.addHandler(tipsHandler, "/todays-tips").setAllowedOrigins("*")
        registry.addHandler(insightHandler, "/insight").setAllowedOrigins("*")
    }
}
