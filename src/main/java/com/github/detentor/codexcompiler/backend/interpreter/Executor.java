package com.github.detentor.codexcompiler.backend.interpreter;

import com.github.detentor.codexcompiler.backend.BackEnd;
import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;
import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageType;

/**
 * Classe padrão de interpretadores.
 * 
 * @author Vinicius Seufitele
 * 
 */
public class Executor extends BackEnd
{

	public Executor(final SymbolTable symbolTable, final IntermediateCode iCode)
	{
		super(symbolTable, iCode);
	}

	/**
	 * Processa o código intermediário e a tabela de símbolos gerada pelo compilador para executar o programa fonte
	 */
	@Override
	public void process()
	{
		final long startTime = System.currentTimeMillis();
		final float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
		final int executionCount = 0;
		final int runtimeErrors = 0;

		// Send the interpreter summary message.
		messageHandler
				.sendMessage(new Message(MessageType.INTERPRETER_SUMMARY, new Number[] { executionCount, runtimeErrors, elapsedTime }));
	}

}
