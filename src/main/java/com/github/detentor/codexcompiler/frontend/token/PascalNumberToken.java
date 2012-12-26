package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.pascal.PascalErrorCode;
import com.github.detentor.codexcompiler.frontend.pascal.PascalToken;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;

/**
 * Essa classe representa um token numérico (número sem sinal)
 * 
 * @author Vinicius Seufitele
 * 
 */
public class PascalNumberToken extends PascalToken
{
	private static final int MAX_EXPONENT = 37;

	public PascalNumberToken(final Source source)
	{
		super(source);
	}

	@Override
	protected void extract()
	{
		final StringBuilder textBuffer = new StringBuilder(); // token's characters
		extractNumber(textBuffer);
		text = textBuffer.toString();
	}

	private void extractNumber(final StringBuilder textBuffer)
	{
		String wholeDigits = null; // digits before the decimal point
		String fractionDigits = null; // digits after the decimal point
		String exponentDigits = null; // exponent digits
		char exponentSign = '+'; // exponent sign '+' or '-'
		boolean sawDotDot = false; // true if saw .. token
		char currentChar; // current character

		type = PascalTokenType.INTEGER; // assume INTEGER token type for now

		// Extract the digits of the whole part of the number.
		wholeDigits = unsignedIntegerDigits(textBuffer);
		if (type == PascalTokenType.ERROR)
		{
			return;
		}

		// Is there a . ?
		// It could be a decimal point or the start of a .. token.
		currentChar = source.currentChar();
		if (currentChar == '.')
		{
			if (source.peekChar() == '.')
			{
				sawDotDot = true; // it's a ".." token, so don't consume it
			}
			else
			{
				type = PascalTokenType.REAL; // decimal point, so token type is REAL
				textBuffer.append(currentChar);
				currentChar = source.nextChar(); // consume decimal point

				// Collect the digits of the fraction part of the number.
				fractionDigits = unsignedIntegerDigits(textBuffer);
				if (type == PascalTokenType.ERROR)
				{
					return;
				}
			}
		}

		// Is there an exponent part?
		// There cannot be an exponent if we already saw a ".." token.
		currentChar = source.currentChar();

		if (!sawDotDot && (currentChar == 'E' || currentChar == 'e'))
		{
			type = PascalTokenType.REAL; // exponent, so token type is REAL
			textBuffer.append(currentChar);
			currentChar = source.nextChar(); // consume 'E' or 'e'

			// Exponent sign?
			if (currentChar == '+' || currentChar == '-')
			{
				textBuffer.append(currentChar);
				exponentSign = currentChar;
				currentChar = source.nextChar(); // consume '+' or '-'
			}

			// Extract the digits of the exponent.
			exponentDigits = unsignedIntegerDigits(textBuffer);
		}

		// Compute the value of an integer number token.
		if (type == PascalTokenType.INTEGER)
		{
			final int integerValue = computeIntegerValue(wholeDigits);

			if (type != PascalTokenType.ERROR)
			{
				value = new Integer(integerValue);
			}
		}
		// Compute the value of a real number token.
		else if (type == PascalTokenType.REAL)
		{
			final float floatValue = computeFloatValue(wholeDigits, fractionDigits, exponentDigits, exponentSign);

			if (type != PascalTokenType.ERROR)
			{
				value = new Float(floatValue);
			}
		}
	}

	/**
	 * Computa o valor de um float a partir de uma String
	 * @param wholeDigits
	 * @param fractionDigits
	 * @param exponentDigits
	 * @param exponentSign
	 * @return
	 */
	private float computeFloatValue(final String wholeDigits, final String fractionDigits, final String exponentDigits,
			final char exponentSign)
	{
		double floatValue = 0.0;
		int exponentValue = computeIntegerValue(exponentDigits);
		String digits = wholeDigits; // whole and fraction digits

		// Negate the exponent if the exponent sign is '-'.
		if (exponentSign == '-')
		{
			exponentValue = -exponentValue;
		}

		// If there are any fraction digits, adjust the exponent value
		// and append the fraction digits.
		if (fractionDigits != null)
		{
			exponentValue -= fractionDigits.length();
			digits += fractionDigits;
		}

		// Check for a real number out of range error.
		if (Math.abs(exponentValue + wholeDigits.length()) > MAX_EXPONENT)
		{
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.RANGE_REAL;
			return 0.0f;
		}

		// Loop over the digits to compute the float value.
		int index = 0;
		while (index < digits.length())
		{
			floatValue = 10 * floatValue + Character.getNumericValue(digits.charAt(index++));
		}

		// Adjust the float value based on the exponent value.
		if (exponentValue != 0)
		{
			floatValue *= Math.pow(10, exponentValue);
		}
		return (float) floatValue;
	}

	/**
	 * Transforma a String de dígitos passada como parâmetro em um número inteiro
	 * 
	 * @param digits
	 * @return
	 */
	private int computeIntegerValue(final String digits)
	{
		// Return 0 if no digits.
		if (digits == null)
		{
			return 0;
		}

		int integerValue = 0;
		int prevValue = -1; // overflow occurred if prevValue > integerValue
		int index = 0;

		// Loop over the digits to compute the integer value
		// as long as there is no overflow.
		while (index < digits.length() && integerValue >= prevValue)
		{
			prevValue = integerValue;
			integerValue = 10 * integerValue + Character.getNumericValue(digits.charAt(index++));
		}

		// No overflow: Return the integer value.
		if (integerValue >= prevValue)
		{
			return integerValue;
		}

		// Overflow: Set the integer out of range error.
		{
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.RANGE_INTEGER;
			return 0;
		}
	}

	/**
	 * Extrai os dígitos da String passada como parâmetro
	 * 
	 * @param textBuffer
	 * @return
	 */
	private String unsignedIntegerDigits(final StringBuilder textBuffer)
	{
		char currentChar = source.currentChar();

		// Must have at least one digit.
		if (!Character.isDigit(currentChar))
		{
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.INVALID_NUMBER;
			return null;
		}

		// Extract the digits.
		final StringBuilder digits = new StringBuilder();
		while (Character.isDigit(currentChar))
		{
			textBuffer.append(currentChar);
			digits.append(currentChar);
			currentChar = source.nextChar(); // consume digit
		}

		return digits.toString();
	}

}
