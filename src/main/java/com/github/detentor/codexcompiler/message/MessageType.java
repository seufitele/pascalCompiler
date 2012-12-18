package com.github.detentor.codexcompiler.message;

/**
 * Representa os tipos de mensagens que podem ser enviadas 
 * @author Vinicius Seufitele
 *
 */
public enum MessageType
{
	SOURCE_LINE, 
	SYNTAX_ERROR, 
    PARSER_SUMMARY, 
    INTERPRETER_SUMMARY, 
    COMPILER_SUMMARY,
    MISCELLANEOUS, 
    TOKEN,
    ASSIGN, 
    FETCH, 
    BREAKPOINT, 
    RUNTIME_ERROR,
    CALL, 
    RETURN,
}
