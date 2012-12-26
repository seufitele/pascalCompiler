package com.github.detentor.codexcompiler.frontend.pascal;

import com.github.detentor.codexcompiler.frontend.Parser;
import com.github.detentor.codexcompiler.frontend.Scanner;
import com.github.detentor.codexcompiler.frontend.Token;
import com.github.detentor.codexcompiler.frontend.TokenType;
import com.github.detentor.codexcompiler.frontend.token.EofToken;
import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageType;

/**
 * Parser TD (Top-down) para a linguagem Pascal.
 * 
 * @author Vinicius Seufitele
 * 
 */
public class PascalParserTD extends Parser
{
	protected PascalErrorHandler errorHandler = new PascalErrorHandler();

	/**
	 * Cria um Parser de Pascal para o Scanner passado como parâmetro
	 * @param scanner
	 */
	public PascalParserTD(final Scanner scanner)
	{
		super(scanner);
	}

	@Override
	public void parse()
	{
		Token token;
		final long startTime = System.currentTimeMillis();

		// Pula os tokens até chegar no token end-of-file
		while (!((token = scanner.nextToken()) instanceof EofToken))
		{
			final TokenType tokenType = token.getType();
			
			if (tokenType == PascalTokenType.ERROR)
			{
				errorHandler.flag(token, (PascalErrorCode)token.getValue(), this);
			}
			else
			{
				messageHandler.sendMessage(new Message(MessageType.TOKEN, 
						new Object[] { token.getLineNum(), token.getPosition(), tokenType, token.getText(), token.getValue() }));
			}
		}

		// Send the parser summary message.
		final float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;

		messageHandler.sendMessage(new Message(MessageType.PARSER_SUMMARY,
				new Number[] { token.getLineNum(), getErrorCount(), elapsedTime }));
	}

	@Override
	public int getErrorCount()
	{
		return errorHandler.getErrorCount();
	}

}
