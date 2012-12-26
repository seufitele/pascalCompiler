package com.github.detentor.codexcompiler.frontend.pascal;

import com.github.detentor.codexcompiler.frontend.Parser;
import com.github.detentor.codexcompiler.frontend.Token;
import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageType;


/**
 * Cuida dos erros de sintaxe da linguagem Pascal
 * 
 * @author Vinicius Seufitele
 *
 */
public class PascalErrorHandler
{
	 private static final int MAX_ERRORS = 25;
	 private int errorCount = 0;   // count of syntax errors
	 
	/**
	 * Marca um erro na linha fonte
	 * @param token o token que representa o erro
	 * @param errorCode O código de erro
	 * @param parser O parser
	 */
	public void flag(final Token token, final PascalErrorCode errorCode, final Parser parser)
	{
	    // Notify the parser's listeners.
		parser.getMessageHandler().sendMessage(new Message(MessageType.SYNTAX_ERROR,
                                       new Object[] {token.getLineNum(),
                                                     token.getPosition(),
                                                     token.getText(),
                                                     errorCode.toString()}));
        if (++errorCount > MAX_ERRORS) 
        {
            abortTranslation(PascalErrorCode.TOO_MANY_ERRORS, parser);
        }
    }
	 
	/**
	 * Cancela o parse do arquivo
	 * @param errorCode O código de erro
	 * @param parser O parser
	 */
	public void abortTranslation(final PascalErrorCode errorCode, final Parser parser)
	{
	    // Notify the parser's listeners and then abort.
	    final String fatalText = "FATAL ERROR: " + errorCode.toString();
	    parser.getMessageHandler().sendMessage(new Message(MessageType.SYNTAX_ERROR, new Object[] {0, 0, "", fatalText}));
	    System.exit(errorCode.getStatus());
	}

	public int getErrorCount()
	{
		return errorCount;
	}

}
