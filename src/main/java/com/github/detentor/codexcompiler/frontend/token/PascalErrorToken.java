package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.pascal.PascalErrorCode;
import com.github.detentor.codexcompiler.frontend.pascal.PascalToken;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;

/**
 * Representa o token de erros do pascal
 * @author Vinicius Seufitele
 *
 */
public class PascalErrorToken extends PascalToken
{

	public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText)
	{
		super(source);

		this.text = tokenText;
		this.type = PascalTokenType.ERROR;
		this.value = errorCode;
	}
	
	protected void extract()
	{
		
	}
	

}
