package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.pascal.PascalErrorCode;
import com.github.detentor.codexcompiler.frontend.pascal.PascalToken;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;

/**
 * Essa classe representa um token de símbolos especiais do Pascal (ponto, vírgula, mais, menos, etc.)
 * 
 * @author Vinicius Seufitele
 * 
 */
public class PascalSpecialSymbolToken extends PascalToken
{

	public PascalSpecialSymbolToken(Source source)
	{
		super(source);
	}

	@Override
	protected void extract()  
	{
		//PROBLEMAS: EXCESSIVAMENTE DEPENDENTE DO OUTRO CÓDIGO. Dado que os símbolos já foram definidos, isso não poderia ser feito
		//automaticamente?
		
		char currentChar = source.currentChar();

		text = Character.toString(currentChar);
		type = null;

		switch (currentChar)
		{

			// Single-character special symbols.
			case '+':
			case '-':
			case '*':
			case '/':
			case ',':
			case ';':
			case '\'':
			case '=':
			case '(':
			case ')':
			case '[':
			case ']':
			case '{':
			case '}':
			case '^':
			{
				source.nextChar(); // consume character
				break;
			}
	
			// : or :=
			case ':':
			{
				currentChar = source.nextChar(); // consume ':';
	
				if (currentChar == '=')
				{
					text += currentChar;
					source.nextChar(); // consume '='
				}
				break;
			}
	
			// < or <= or <>
			case '<':
			{
				currentChar = source.nextChar(); // consume '<';
	
				if (currentChar == '=')
				{
					text += currentChar;
					source.nextChar(); // consume '='
				}
				else if (currentChar == '>')
				{
					text += currentChar;
					source.nextChar(); // consume '>'
				}
				break;
			}
	
			// > or >=
			case '>':
			{
				currentChar = source.nextChar(); // consume '>';
	
				if (currentChar == '=')
				{
					text += currentChar;
					source.nextChar(); // consume '='
				}
				break;
			}
	
			// . or ..
			case '.':
			{
				currentChar = source.nextChar(); // consume '.';
	
				if (currentChar == '.')
				{
					text += currentChar;
					source.nextChar(); // consume '.'
				}
				break;
			}
	
			default:
			{
				source.nextChar(); // consume bad character
				type = PascalTokenType.ERROR;
				value = PascalErrorCode.INVALID_CHARACTER;
			}
		}

		// Set the type if it wasn't an error.
		if (type == null)
		{
			type = PascalTokenType.SPECIAL_SYMBOLS.get(text);
		}
	}

}
