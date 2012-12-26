package com.github.detentor.codexcompiler.intermediate;

import java.util.List;

/**
 * Representa a tabela de símbolos
 * 
 * @author Vinicius Seufitele
 * 
 */
public interface SymbolTable
{
	/**
	 * Getter.
	 * 
	 * @return the scope nesting level of this entry.
	 */
	public int getNestingLevel();

	/**
	 * Cria e insere um novo elemento na tabela de símbolos
	 * 
	 * @param name the name of the entry.
	 * @return the new entry.
	 */
	public SymbolTableEntry enter(String name);

	/**
	 * Procura por um item na tabela de símbolos
	 * 
	 * @param name O nome do elemento a ser procurado
	 * @return O elemento, ou null se ele não existir
	 */
	public SymbolTableEntry lookup(String name);

	/**
	 * @return Retorna uma lista de nomes de símbolos, ordenados pelo nome
	 */
	public List<SymbolTableEntry> sortedEntries();

}
