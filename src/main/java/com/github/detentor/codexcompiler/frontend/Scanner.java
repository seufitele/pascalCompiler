package com.github.detentor.codexcompiler.frontend;

/**
 * Classe abstrata que representa o Scanner. <br/>
 * O Scanner lê um arquivo-fonte e converte os caracteres em Tokens. Esses tokens
 * representam os pequenos blocos da linguagem. Ex: identificadores, palavras-chave, operadores, etc.
 * 
 * @author Vinícius Seufitele Pinto
 *
 */
public abstract class Scanner
{
	protected final Source source; // source
	private Token currentToken; // current token

	/**
	 * Cria um Scanner para o arquivo-fonte passado como parâmetro
	 * @param source
	 */
	protected Scanner(final Source source)
	{
		this.source = source;
	}

	/**
	 * Pega o token atual
	 */
	public Token currentToken()
	{
		return currentToken;
	}

	/**
	 * Retorna o próximo token a partir do arquivo fonte
	 * 
	 * @return O próximo token extraído do arquivo fonte
	 */
	public Token nextToken()
	{
		return currentToken = extractToken();
	}

	/**
	 * Extrai e retorna o próximo token a partir do arquivo fonte. <br/>
	 * 
	 * @return O próximo token
	 */
	protected abstract Token extractToken();

	/**
	 * Chama o método {@link Source#currentChar() currentChar} do arquivo fonte
	 * 
	 * @return O caracter atual do arquivo fonte
	 */
	public char currentChar() 
	{
		return source.currentChar();
	}

	/**
	 * Chama o método {@link Source#nextChar() nextChar} do arquivo fonte
	 * 
	 * @return O próximo caracter do arquivo fonte
	 */
	public char nextChar() 
	{
		return source.nextChar();
	}

}
