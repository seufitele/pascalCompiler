package com.github.detentor.codexcompiler.frontend;

import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;
import com.github.detentor.codexcompiler.message.MessageHandler;

/**
 * Essa é a classe base para os Parsers. <br/>
 * 
 * 
 * @author Vinícius Seufitele
 *
 */
public abstract class Parser
{
	protected SymbolTable symbolTable = null;  // generated symbol table
	protected final MessageHandler messageHandler = new MessageHandler();
	
    protected Scanner scanner; // scanner used with this parser
    protected IntermediateCode iCode;     // intermediate code generated by this parser
 
    /**
     * Constrói um parser a partir do Scanner passado como parâmetro
     * @param scanner
     */
    protected Parser(Scanner scanner)
    {
        this.scanner = scanner;
        this.iCode = null;
    }
 
    /**
     * Faz o parse do programa fonte e gera o código intermediário e a tabela de símbolos. <br/>
     * A ser implementado pela subclasse de uma linguagem específica. <br/>
     */
    public abstract void parse();
 
    /**
     * Retorna o número de erros de sintaxe encontrados pelo parser. <br/>
     * A ser implementado pela subclasse de uma linguagem específica. <br/>
     * @return O número de erros
     */
    public abstract int getErrorCount();

    
    
	public SymbolTable getSymbolTable()
	{
		return symbolTable;
	}

	public IntermediateCode getIntermediateCode()
	{
		return iCode;
	}

	public MessageHandler getMessageHandler()
	{
		return messageHandler;
	}
	
}
