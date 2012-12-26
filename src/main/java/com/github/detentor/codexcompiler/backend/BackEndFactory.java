package com.github.detentor.codexcompiler.backend;

import com.github.detentor.codexcompiler.backend.compiler.CodeGenerator;
import com.github.detentor.codexcompiler.backend.interpreter.Executor;
import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;

/**
 * Fábrica para construir componentes back-end (interpretadores ou compiladores)
 * 
 * @author Vinicius Seufitele
 *
 */
public class BackEndFactory
{
	public enum BackEndType {COMPILER, INTERPRETER }
	
	
	/**
	 * Cria um componente back-end para o tipo especificado
	 * @param forType
	 * @return
	 */
	public static BackEnd createBackEnd(final BackEndType forType, SymbolTable symTable, IntermediateCode iCode)
	{
		if (forType == BackEndType.COMPILER)
		{
			return new CodeGenerator(symTable, iCode);
		}
		else if (forType == BackEndType.INTERPRETER)
		{
			return new Executor(symTable, iCode);
		}
		throw new IllegalArgumentException("Tipo não definido");
	}

}
