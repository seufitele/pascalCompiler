package com.github.detentor.codexcompiler.message;

import java.util.ArrayList;
import java.util.List;

/**
 * O handler gerencia os listeners e envia pra eles as mensagens 
 * 
 * @author Vinicius Seufitele
 *
 */
public class MessageHandler implements MessageProducer
{
	private final List<MessageListener> listeners = new ArrayList<MessageListener>();

	
	public void addMessageListener(final MessageListener listener)
	{
		listeners.add(listener);
	}

	public void removeMessageListener(final MessageListener listener)
	{
		listeners.remove(listener);
	}

	public void sendMessage(final Message message)
	{
		for (final MessageListener mess : listeners)
		{
			mess.messageReceived(message);
		}
	}

}
