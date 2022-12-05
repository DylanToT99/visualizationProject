package com.example.springboot_server.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/18 20:43
 * @description TODO
 **/
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 作用
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
