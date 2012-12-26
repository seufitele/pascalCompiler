package com.github.detentor.codexcompiler.intermediate;

/**
 * A interface para a pilha da tabela de símbolos
 * 
 * @author Vinicius Seufitele
 * 
 */
public interface SymbolTableStack
{

	/**
	 * Retorna o nível atual de aninhamento
	 * 
	 * @return
	 */
	public int getCurrentNestingLevel();

	/**
	 * Retorna a tabela local de símbolos que está no topo da pilha
	 * 
	 * @return
	 */
	public SymbolTable getLocalSymbolTable();

	/**
	 * Cria e insere um novo elemento na tabela local de símbolos
	 * 
	 * @param name O nome do novo elemento
	 * @return O novo elemento
	 */
	public SymbolTableEntry enterLocal(String name);

	/**
	 * Procura por um símbolo na tabela de símbolos local
	 * 
	 * @param name O nome do elemento.
	 * @return O elemento, ou null se ele não existir.
	 */
	public SymbolTableEntry lookupLocal(String name);

	/**
	 * Procura por um símbolo em toda a pilha de tabelas de símbolos
	 * 
	 * @param name O nome do elemento.
	 * @return O elemento, ou null se ele não existir
	 */
	public SymbolTableEntry lookup(String name);

}
