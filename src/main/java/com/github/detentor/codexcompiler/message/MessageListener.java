package com.github.detentor.codexcompiler.message;

public interface MessageListener
{
	/**
	 * Chamado para receber uma mensgaem enviada por um produtor de mensagens
	 * @param message A mensagem que foi enviada
	 */
    void messageReceived(Message message);
}
