package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.pascal.PascalToken;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;

/**
 * Representa uma "palavra" do Pascal. A palavra pode ser um identificador (nome de variável) ou 
 * uma palavra reservada.
 * @author Vinicius Seufitele
 *
 */
public class PascalWordToken extends PascalToken
{

	public PascalWordToken(final Source source)
	{
		super(source);
	}

	/**
	 * {@inheritDoc}<br/>
	 * Extrai o token referente à uma word do Pascal, setando o seu valor como os caracteres extraídos
	 */
	@Override
	protected void extract()
	{
		final StringBuilder textBuffer = new StringBuilder();
		char currentChar = source.currentChar();

		// Get the word characters (letter or digit). The scanner has
		// already determined that the first character is a letter.
		while (Character.isLetterOrDigit(currentChar))
		{
			textBuffer.append(currentChar);
			currentChar = source.nextChar(); // consume character
		}

		text = textBuffer.toString();

		// Is it a reserved word or an identifier?
		type = PascalTokenType.RESERVED_WORDS.contains(text.toLowerCase()) ? PascalTokenType.valueOf(text.toUpperCase()) // reserved word
				: PascalTokenType.IDENTIFIER; // identifier
	}

}
