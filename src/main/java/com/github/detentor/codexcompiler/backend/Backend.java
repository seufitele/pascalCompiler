package com.github.detentor.codexcompiler.backend;

import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;
import com.github.detentor.codexcompiler.message.MessageHandler;

/**
 * A classe do framework que representa o componente do backend. Esse componente
 * pode ser um interpretador ou um compilador. <br/><br/>
 * 
 * Um interpretador criará o método {@link #process()} para executar as instruções do código intermediário. <br/><br/>
 * 
 * Um compilador criará o método {@link Process} para gerar o código-objeto.
 * 
 * @author Vinicius Seufitele
 *
 */
public abstract class Backend
{
	protected static MessageHandler messageHandler = new MessageHandler();
	protected SymbolTable symbolTable;
	protected IntermediateCode iCode;
	
	

	public abstract void process(); 
	
}
