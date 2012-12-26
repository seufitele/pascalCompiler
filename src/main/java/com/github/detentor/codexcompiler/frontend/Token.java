package com.github.detentor.codexcompiler.frontend;

/**
 * O token é uma abstração de um elemento atômico retornado a partir do Scanner.
 * 
 * @author Vinicius Seufitele
 * 
 */
public class Token
{
	protected TokenType type; // language-specific token type
	protected String text; // token text
	protected Object value; // token value
	protected Source source; // source
	protected int lineNum; // line number of the token's source line
	protected int position; // position of the first token character

	/**
	 * Constrói um token para o arquivo-fonte passado como parâmetro
	 * 
	 * @param source O arquivo-fonte de onde os tokens serão extraídos
	 */
	public Token(final Source source)
	{
		this.source = source;
		this.lineNum = source.getLineNum();
		this.position = source.getCurrentPos();
		extract();
	}

	/**
	 * Método padrão para extrair somente tokens de um caracter a partir do arquivo-fonte. <br/>
	 * Subclasses podem sobrescrever este método para construir tokens específicos para uma linguagem. <br/>
	 * Depois de extrair o token, a posição da linha será uma após o último caracter do token. <br/>
	 */
	protected void extract()
	{
		text = Character.toString(source.currentChar());
		value = null;
		source.nextChar(); // consume current character
	}

	public int getLineNum()
	{
		return lineNum;
	}

	public void setLineNum(final int lineNum)
	{
		this.lineNum = lineNum;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(final int position)
	{
		this.position = position;
	}

	public TokenType getType()
	{
		return type;
	}

	public void setType(final TokenType type)
	{
		this.type = type;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

	public String getText()
	{
		return text;
	}
}
