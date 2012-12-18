package com.github.detentor.codexcompiler.frontend.token;

import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.Token;

/**
 * Token genérico que representa o fim do arquivo
 * 
 * @author Vinicius Seufitele
 * 
 */
public class EofToken extends Token
{

	public EofToken(final Source source)
	{
		super(source);
	}

	@Override
	protected void extract()
	{
		// Não faz nada
	}

}
