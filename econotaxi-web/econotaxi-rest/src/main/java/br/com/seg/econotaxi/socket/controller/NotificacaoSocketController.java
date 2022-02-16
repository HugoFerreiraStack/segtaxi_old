package br.com.seg.econotaxi.socket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.com.seg.econotaxi.socket.message.SocketMessage;

@Controller
public class NotificacaoSocketController {

	@MessageMapping("/solicitar-corrida")
	@SendTo("/topic/notificar-solicitacao-corrida")
	public SocketMessage notificar(SocketMessage socketMessage) {
		return socketMessage;
	}
	
}
