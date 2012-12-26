package com.github.detentor.codexcompiler.other;

import java.io.BufferedReader;
import java.io.FileReader;

import com.github.detentor.codexcompiler.backend.BackEnd;
import com.github.detentor.codexcompiler.backend.BackEndFactory;
import com.github.detentor.codexcompiler.backend.BackEndFactory.BackEndType;
import com.github.detentor.codexcompiler.frontend.Parser;
import com.github.detentor.codexcompiler.frontend.ParserFactory;
import com.github.detentor.codexcompiler.frontend.ParserFactory.Language;
import com.github.detentor.codexcompiler.frontend.ParserFactory.ParserType;
import com.github.detentor.codexcompiler.frontend.pascal.PascalTokenType;
import com.github.detentor.codexcompiler.frontend.Source;
import com.github.detentor.codexcompiler.frontend.TokenType;
import com.github.detentor.codexcompiler.intermediate.IntermediateCode;
import com.github.detentor.codexcompiler.intermediate.SymbolTable;
import com.github.detentor.codexcompiler.message.Message;
import com.github.detentor.codexcompiler.message.MessageHandler;
import com.github.detentor.codexcompiler.message.MessageListener;

public class Pascal
{
	private Parser parser; // language-independent parser
	private Source source; // language-independent scanner
	private IntermediateCode iCode; // generated intermediate code
	private SymbolTable symTab; // generated symbol table
	private BackEnd backend; // backend

	/**
	 * Compile or interpret a Pascal source program.
	 * 
	 * @param operation either "compile" or "execute".
	 * @param filePath the source file path.
	 * @param flags the command line flags.
	 */
	public Pascal(final String operation, final String filePath, final String flags)
	{
		try
		{
			// final boolean intermediate = flags.indexOf('i') > -1;
			// final boolean xref = flags.indexOf('x') > -1;

			source = new Source(new BufferedReader(new FileReader(filePath)), new MessageHandler());
			source.getMessageHandler().addMessageListener(new SourceMessageListener());

			parser = ParserFactory.createParser(Language.PASCAL, ParserType.TOP_DOWN, source);
			parser.getMessageHandler().addMessageListener(new ParserMessageListener());

			parser.parse();
			source.close();

			final BackEndType opType = operation.trim().equalsIgnoreCase("compile") ? BackEndType.COMPILER : BackEndType.INTERPRETER;
			backend = BackEndFactory.createBackEnd(opType, parser.getSymbolTable(), parser.getIntermediateCode());
			backend.getMessageHandler().addMessageListener(new BackendMessageListener());

			backend.process();
		}
		catch (final Exception ex)
		{
			System.out.println("***** Internal translator error. *****");
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args command-line arguments: "compile" or "execute" followed by optional flags followed by the source file path.
	 */
	public static void main(final String[] args)
	{
		final String FLAGS = "[-ix]";
		final String USAGE = "Usage: Pascal execute|compile " + FLAGS + " <source file path>";

		final String operation = args[0];

		// Operation.
		if (!(operation.equalsIgnoreCase("compile") || operation.equalsIgnoreCase("execute")))
		{
			System.out.println(USAGE);
			return;
		}

		int i = 0;
		String flags = "";

		// Flags.
		while (++i < args.length && args[i].charAt(0) == '-')
		{
			flags += args[i].substring(1);
		}

		// Source path.
		if (i >= args.length)
		{
			System.out.println(USAGE);
			return;
		}
		final String path = args[i];
		new Pascal(operation, path, flags);
	}

	/**
	 * Listener for source messages.
	 */
	private class SourceMessageListener implements MessageListener
	{
		private final String SOURCE_LINE_FORMAT = "%03d %s";

		public void messageReceived(final Message message)
		{
			switch (message.getType())
			{
			case SOURCE_LINE:
			{
				final Object body[] = (Object[]) message.getBody();
				final int lineNumber = (Integer) body[0];
				final String lineText = (String) body[1];
				System.out.println(String.format(SOURCE_LINE_FORMAT, lineNumber, lineText));
				break;
			}
			default:
				throw new IllegalArgumentException("tipo de mensagem não reconhecido");
			}
		}
	}

	/**
	 * Listener for parser messages.
	 */
	private class ParserMessageListener implements MessageListener
	{
		private final String PARSER_SUMMARY_FORMAT = "\n%,20d source lines." + "\n%,20d syntax errors."
				+ "\n%,20.2f seconds total parsing time.\n";

		private static final String TOKEN_FORMAT = ">>> %-15s line=%03d, pos=%2d, text=\"%s\"";

		private static final String VALUE_FORMAT = ">>>                 value=%s";

		private static final int PREFIX_WIDTH = 5;

		public void messageReceived(final Message message)
		{
			switch (message.getType())
			{
				case TOKEN:
				{
					final Object body[] = (Object[]) message.getBody();
					final int line = (Integer) body[0];
					final int position = (Integer) body[1];
					final TokenType tokenType = (TokenType) body[2];
					final String tokenText = (String) body[3];
					Object tokenValue = body[4];
	
					System.out.println(String.format(TOKEN_FORMAT, tokenType, line, position, tokenText));
					if (tokenValue != null)
					{
						if (tokenType == PascalTokenType.STRING)
						{
							tokenValue = "\"" + tokenValue + "\"";
						}
						System.out.println(String.format(VALUE_FORMAT, tokenValue));
					}
					break;
				}
	
				case SYNTAX_ERROR:
				{
					final Object body[] = (Object[]) message.getBody();
					final int lineNumber = (Integer) body[0];
					final int position = (Integer) body[1];
					final String tokenText = (String) body[2];
					final String errorMessage = (String) body[3];
	
					final int spaceCount = PREFIX_WIDTH + position;
					final StringBuilder flagBuffer = new StringBuilder();
	
					// Spaces up to the error position.
					for (int i = 1; i < spaceCount; ++i)
					{
						flagBuffer.append(' ');
					}
	
					// A pointer to the error followed by the error message.
					flagBuffer.append("^\n*** ").append(errorMessage);
	
					// Text, if any, of the bad token.
					if (tokenText != null)
					{
						flagBuffer.append(" [at \"").append(tokenText).append("\"]");
					}
					System.out.println(flagBuffer.toString());
					break;
				}
	
				case PARSER_SUMMARY:
				{
					final Number body[] = (Number[]) message.getBody();
					final int statementCount = (Integer) body[0];
					final int syntaxErrors = (Integer) body[1];
					final float elapsedTime = (Float) body[2];
	
					System.out.printf(PARSER_SUMMARY_FORMAT, statementCount, syntaxErrors, elapsedTime);
					break;
				}
				default:
					throw new IllegalArgumentException("tipo de mensagem não reconhecido");
			}
		}
	}

	/**
	 * Listener for back end messages.
	 */
	private class BackendMessageListener implements MessageListener
	{
		private final String INTERPRETER_SUMMARY_FORMAT = "\n%,20d statements executed." + "\n%,20d runtime errors."
				+ "\n%,20.2f seconds total execution time.\n";

		private final String COMPILER_SUMMARY_FORMAT = "\n%,20d instructions generated."
				+ "\n%,20.2f seconds total code generation time.\n";

		public void messageReceived(final Message message)
		{
			final Number body[] = (Number[]) message.getBody();

			switch (message.getType())
			{
			case INTERPRETER_SUMMARY:
			{
				final int executionCount = (Integer) body[0];
				final int runtimeErrors = (Integer) body[1];
				final float elapsedTime = (Float) body[2];

				System.out.printf(INTERPRETER_SUMMARY_FORMAT, executionCount, runtimeErrors, elapsedTime);
				break;
			}
			case COMPILER_SUMMARY:
			{
				final int instructionCount = (Integer) body[0];
				final float elapsedTime = (Float) body[1];

				System.out.printf(COMPILER_SUMMARY_FORMAT, instructionCount, elapsedTime);
				break;
			}
			default:
				throw new IllegalArgumentException("tipo de mensagem não reconhecido");
			}
		}
	}

}
