package com.github.detentor.codexcompiler.frontend.pascal;

import com.github.detentor.codexcompiler.frontend.Scanner;
import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.Token;
import com.github.detentor.codexcompiler.frontend.token.EofToken;
import com.github.detentor.codexcompiler.frontend.token.PascalErrorToken;
import com.github.detentor.codexcompiler.frontend.token.PascalNumberToken;
import com.github.detentor.codexcompiler.frontend.token.PascalSpecialSymbolToken;
import com.github.detentor.codexcompiler.frontend.token.PascalStringToken;
import com.github.detentor.codexcompiler.frontend.token.PascalWordToken;

/**
 * Scanner da linguagem Pascal
 * 
 * @author Vinicius Seufitele
 * 
 */
public class PascalScanner extends Scanner
{
	public PascalScanner(final Source source)
	{
		super(source);
	}

	@Override
	protected Token extractToken()
	{
		skipWhiteSpace();

		Token token;
		final char currentChar = currentChar();

		// Construct the next token. The current character determines the
		// token type.
		if (currentChar == Source.EOF)
		{
			token = new EofToken(source);
		}
		else if (Character.isLetter(currentChar)) // Começando com letra é pascal token
		{
			token = new PascalWordToken(source);
		}
		else if (Character.isDigit(currentChar)) // Começando com dígito é número
		{
			token = new PascalNumberToken(source);
		}
		else if (currentChar == '\'') // Começando com aspa simples, é String
		{
			token = new PascalStringToken(source);
		}
		else if (PascalTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) // Verifica se é um símbolo especial
		{
			token = new PascalSpecialSymbolToken(source);
		}
		else
		{
			// Dá erro, e ignora o caracter
			token = new PascalErrorToken(source, PascalErrorCode.INVALID_CHARACTER, Character.toString(currentChar));
			nextChar(); // consume character
		}

		return token;
	}

	/**
	 * Pula os espaços em branco do código fonte (também pula comentários)
	 */
	private void skipWhiteSpace()
	{
		char currentChar = currentChar();

		while (Character.isWhitespace(currentChar) || (currentChar == '{'))
		{
			if (currentChar == '{') // Start of a comment?
			{
				do
				{
					currentChar = nextChar(); // consume comment characters
				} while ((currentChar != '}') && (currentChar != Source.EOF));

				if (currentChar == '}') // Found closing '}'?
				{
					currentChar = nextChar(); // consume the '}'
				}
			}
			else // Not a comment.
			{
				currentChar = nextChar(); // consume whitespace character
			}
		}
	}
}
