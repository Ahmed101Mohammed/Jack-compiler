package parsing.jack_structure;

import java.util.ArrayList;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.SubRoutineDec_C;
import parsing.jack_structure.class_vars.ClassVarDec;
import tokens.IdentifierToken;
import tokens.KeywordToken;
import tokens.Token;
import tokens.TokenType;

public class Class_C extends JackCommand
{
	private Token classKeywordToken;
	private Token className;

	private Token LeftCurlyBracket;
	private ClassVarDec classVarDec;
	private SubRoutineDec subRoutineDec;  
	private Token RightCurlyBracket;
	public static ArrayList<IdentifierToken> classesNames = new ArrayList<>();
	public static ArrayList<Class_C> classes = new ArrayList<>();

	public Class_C()
	{
		this.getClassKeyword();
		this.getClassName();
		this.getLeftCurlyBracket();
		this.classVarDec = new ClassVarDec();
		this.subRoutineDec = new SubRoutineDec(this.classVarDec);
		this.getRightCurlyBracket();
		if(this.RightCurlyBracket != null)
		{
			classes.add(this);
		}
	}

	public boolean isContainMethod(IdentifierToken token)
	{
		for(SubRoutineDec_C subRoutine: this.subRoutineDec.getSubRutines())
		{
			if(subRoutine.getPureName().equals(token.getBody()))
			{
				return true;
			}
		}
		return false;
	}

	public static Class_C getClassByName(IdentifierToken token)
	{
		for(Class_C class_C: classes)
		{
			if(class_C.getPureName().equals(token.getBody()))
			{
				return class_C;
			}
		}
		return null;
	}

	public String getPureName()
	{
		return this.className.getBody();
	}

	public void getClassKeyword()
	{
		Token token = CompilationEngine.advance();

		if(token.getType() == TokenType.Keyword && token.getBody().equals("class"))
		{
			this.classKeywordToken = token;
			System.out.println("Success: Check class keyword.");
		}
		else
		{
			
			Token.allTokensErrors.add(new Error("unExpectedToken", CompilationEngine.expectedRatharThanErrorMessage("'class' Keyword", token.getBody()), token.getPosition()));
			System.out.println("Faile: Check class keyword.");
		}
	}

	public void getClassName()
	{
		Token token = CompilationEngine.advance();
		if(token.getType() == TokenType.Identifier)
		{
			if (isClassDefinedBefore(token.getBody()))
			{
				Token.allTokensErrors.add(new Error("DublicatedClassName", String.format("Class name: %s is used before. [!Classes should have unique name]", token.getBody()), token.getPosition()));
			}
			else
			{
				this.className = token;
				IdentifierToken newIdentifierToken = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
				classesNames.add(newIdentifierToken);
				System.out.println("Success: Check class name.");
			}

		}
		else
		{
			
			Token.allTokensErrors.add(new Error("unExpectedToken", CompilationEngine.expectedRatharThanErrorMessage("[className] Identifier", token.getBody()), token.getPosition()));
			System.out.println("Faile: Check class name.");
		}
	}

	public void getLeftCurlyBracket()
	{
		Token token = CompilationEngine.advance();
		if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
		{
			this.LeftCurlyBracket = token;
			System.out.println("Success: Left curly bracket is exist.");
		}
		else
		{
			Token.allTokensErrors.add(new Error("UnExpectedToken", CompilationEngine.expectedRatharThanErrorMessage("'{' Symbol", token.getBody()), token.getPosition()));
			System.out.println("Faile: Left curly bracket is not exist");
		}
	}

	public void getRightCurlyBracket()
	{
		Token token = CompilationEngine.advance();
		if(token.getType() == TokenType.Symbol && token.getBody().equals("}"))
		{
			this.RightCurlyBracket = token;
			System.out.println("Success: Right curly bracket is exist.");
		}
		else
		{
			Token.allTokensErrors.add(new Error("UnExpectedToken", CompilationEngine.expectedRatharThanErrorMessage("'}' Symbol", token.getBody()), token.getPosition()));
			System.out.println("Faile: Right curly bracket is not exist");
		}
	}

	public static boolean isClassDefinedBefore(String name)
	{
		for(IdentifierToken identifierToken:classesNames)
		{
			if(identifierToken.getBody().equals(name))
			{
				return true;
			}
		}
		return false;
	}

	public String generateVMCode(){return null;}
}
