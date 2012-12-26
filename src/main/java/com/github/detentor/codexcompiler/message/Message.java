package com.github.detentor.codexcompiler.message;

public class Message
{
	private final MessageType type;
    private final Object body;
 
    /**
     * Constructor.
     * @param type the message type.
     * @param body the message body.
     */
    public Message(MessageType type, Object body)
    {
        this.type = type;
        this.body = body;
    }

	public MessageType getType()
	{
		return type;
	}

	public Object getBody()
	{
		return body;
	}
    
}
