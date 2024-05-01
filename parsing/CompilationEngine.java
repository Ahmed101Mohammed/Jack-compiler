package parsing;
import java.util.ArrayList;

import helperClasses.Error;
import parsing.jack_structure.Class_C;
import parsing.jack_structure.class_methods.method_parameter.Parameter_C;
import parsing.jack_structure.class_vars.ClassVarDec_C;
import tokens.Token;
import tokens.TokenType;

public class CompilationEngine
{
	public static ArrayList<Token> programTokens = new ArrayList<>();
	public static ArrayList<ClassVarDec_C> neededReviewForIdentfierType = new ArrayList<>();
	public static ArrayList<Parameter_C> neededReviewParameter = new ArrayList<>();
	public static int currentIndex;
	private Class_C program;
	public CompilationEngine(ArrayList<Token> tokens)
	{
		currentIndex = -1;
		programTokens = tokens;		
		this.program =  new Class_C();
	}

	public static Token advance()
	{
		currentIndex += 1;
		return programTokens.get(currentIndex);
	}

	public static void setCurrentIndex(int index)
	{
		currentIndex = index;
	}

	public static void decrementCurrentIndexByOne()
	{
		currentIndex -= 1;
	}

	public static Token getSpecificToken(int index)
	{
		return programTokens.get(index);
	}

	public Class_C getProgram() {
		return program;
	}

	public static int getCurrentIndex()
	{
		return currentIndex;
	}

	public static void checkAllVariablesTypesValidation()	
	{
		for(ClassVarDec_C var:neededReviewForIdentfierType )
		{
			if(!Class_C.isClassDefinedBefore(var.getType().getBody()))
			{
				Token.allTokensErrors.add(new Error("InValidVariableType", 
										"This type: "+ var.getType().getBody() +" deos not valid. Jack accept one of these types: 'int' 'char' 'boolean' [Predefined Class Name].",
										var.getType().getPosition()));
			}
		}
	}

	
	public void generateParsingResultInXMLFormate()
	{
		// Generate xml file.
	}

	public static String expectedRatharThanErrorMessage(String expectedToken, String existToken)
	{
		return String.format("Expected %s, rather than this token: %s", expectedToken, existToken);
	}
}
