package com.github.detentor.codexcompiler.frontend;

import java.io.BufferedReader;
import java.io.IOException;

import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageHandler;
import com.github.detentor.codexcompiler.message.MessageType;

/**
 * A classe que representa o fonte de um programa a ser compilado
 * 
 * @author Vinícius Seufitele
 * 
 */
public class Source
{
	public static final char EOL = '\n'; // end-of-line character
	public static final char EOF = (char) 0; // end-of-file character

	protected final MessageHandler messageHandler = new MessageHandler();
	protected final BufferedReader reader; // reader for the source program
	protected String line; // source line
	protected int lineNum; // current source line number
	protected int currentPos; // current source line position

	/**
	 * Cria um novo arquivo de fonte para um buffer de leitura passado como parâmetro
	 * 
	 * @param reader
	 */
	public Source(final BufferedReader reader, final MessageHandler messageHandler)
	{
		this.lineNum = 0;
		this.currentPos = -2; // set to -2 to read the first source line
		this.reader = reader;
	}

	/**
	 * Retorna o caracter fonte na posição atual
	 * 
	 * @return O caracter da posição atual
	 */
	public char currentChar()
	{
		//NOTAS DE IMPLEMENTAÇÃO:
		//Esse método funciona utilizando a linha atual como 'buffer': quando a linha acaba,
		//ele pega a linha novamente do arquivo. Pode-se substituir por um iterador de arquivo em linhas.
		//só observar porque o iterador deve retornar, no fim de cada, o caracter de fim de linha (EOL), e,
		//no fim da iteração, também.
		
		// First time?
		if (currentPos == -2)
		{
			readLine();
			return nextChar();
		}
		else if (line == null) 	// At end of file?
		{
			return EOF;
		}
		else if ((currentPos == -1) || (currentPos == line.length())) 	// At end of line?
		{
			return EOL;
		}
		else if (currentPos > line.length()) // Need to read the next line?
		{
			readLine();
			return nextChar();
		}
		else // Return the character at the current position.
		{
			return line.charAt(currentPos);
		}
	}

	/**
	 * Consome o caracter atual e retorna o próximo caracter
	 * 
	 * @return O próximo caracter do fonte
	 */
	public char nextChar()
	{
		++currentPos;
		return currentChar();
	}

	/**
	 * Retorna o próximo caracter fonte sem consumir o caracter atual
	 * 
	 * @return O próximo caracter, sem andar com o cursor
	 */
	public char peekChar()
	{
		currentChar();
		if (line == null)
		{
			return EOF;
		}
		int nextPos = currentPos + 1;
		return nextPos < line.length() ? line.charAt(nextPos) : EOL;
	}

	/**
	 * Lê a próxima linha do fonte
	 */
	private void readLine()
	{
		try
		{
			line = reader.readLine();
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException(e);
		}
		currentPos = -1;
		if (line != null)
		{
			++lineNum;
			messageHandler.sendMessage(new Message(MessageType.SOURCE_LINE, new Object[] {lineNum, line}));
		}
	}

	/**
	 * Fecha o arquivo fonte
	 */
	public void close()
	{
		if (reader != null)
		{
			try
			{
				reader.close();
			}
			catch (IOException ex)
			{
				throw new IllegalArgumentException(ex);
			}
		}
	}

	public int getLineNum()
	{
		return lineNum;
	}

	public int getCurrentPos()
	{
		return currentPos;
	}

	public MessageHandler getMessageHandler()
	{
		return messageHandler;
	}
	
}
