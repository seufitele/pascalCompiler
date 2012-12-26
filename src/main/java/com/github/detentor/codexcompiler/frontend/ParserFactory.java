package com.github.detentor.codexcompiler.frontend;

import com.github.detentor.codexcompiler.frontend.pascal.PascalParserTD;
import com.github.detentor.codexcompiler.frontend.pascal.PascalScanner;

/**
 * Fábrica de Parsers
 * 
 * @author Vinicius Seufitele
 *
 */
public class ParserFactory
{
	public enum Language {PASCAL}
	public enum ParserType {TOP_DOWN, BOTTOM_UP}
	
	public static Parser createParser(Language theLanguage, ParserType parserType, Source forSource)
	{
		if (theLanguage == Language.PASCAL && parserType == ParserType.TOP_DOWN)
		{
			final PascalScanner scanner = new PascalScanner(forSource);
			return new PascalParserTD(scanner);
		}
		throw new IllegalArgumentException("Parser não reconhecido");
	}

}
