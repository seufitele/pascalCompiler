package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.pascal.PascalErrorCode;
import com.github.detentor.codexcompiler.frontend.pascal.PascalToken;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;

/**
 * Representa um token de String (sequência de caracteres delimitados por aspas simples ' ' )
 * 
 * @author Vinicius Seufitele
 * 
 */
public class PascalStringToken extends PascalToken
{

	public PascalStringToken(final Source source)
	{
		super(source);
	}

	@Override
	protected void extract()
	{
		final StringBuilder textBuffer = new StringBuilder();
		final StringBuilder valueBuffer = new StringBuilder();

		char currentChar = source.nextChar(); // consume initial quote
		textBuffer.append('\'');

		// Get string characters.
		do
		{
			// Replace any whitespace character with a blank.
			if (Character.isWhitespace(currentChar))
			{
				currentChar = ' ';
			}

			if (currentChar != '\'' && currentChar != Source.EOF)
			{
				textBuffer.append(currentChar);
				valueBuffer.append(currentChar);
				currentChar = source.nextChar(); // consume character
			}

			// Quote? Each pair of adjacent quotes represents a single-quote.
			if (currentChar == '\'')
			{
				while (currentChar == '\'' && source.peekChar() == '\'')
				{
					textBuffer.append("''");
					valueBuffer.append(currentChar); // append single-quote
					currentChar = source.nextChar(); // consume pair of quotes
					currentChar = source.nextChar();
				}
			}
		}
		while (currentChar != '\'' && currentChar != Source.EOF);

		if (currentChar == '\'')
		{
			source.nextChar(); // consume final quote
			textBuffer.append('\'');
			type = PascalTokenType.STRING;
			value = valueBuffer.toString();
		}
		else
		{
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.UNEXPECTED_EOF;
		}
		text = textBuffer.toString();
	}
}
