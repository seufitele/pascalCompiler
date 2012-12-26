package com.github.detentor.codexcompiler.backend.compiler;

import com.github.detentor.codexcompiler.backend.BackEnd;
import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;
import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageType;

/**
 * A implementação default de geradores de código (compiladores)
 * 
 * @author Vinicius Seufitele
 * 
 */
public class CodeGenerator extends BackEnd
{

	public CodeGenerator(final SymbolTable symbolTable, final IntermediateCode iCode)
	{
		super(symbolTable, iCode);
	}

	/**
	 * Processa o código intermediário e a tabela de símbolos gerada pelo parser para criar as instruções em código de máquina
	 */
	@Override
	public void process()
	{
		final long startTime = System.currentTimeMillis();
		final float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
		final int instructionCount = 0;

		// Send the compiler summary message.
		messageHandler.sendMessage(new Message(MessageType.COMPILER_SUMMARY, new Number[] { instructionCount, elapsedTime }));

	}

}
