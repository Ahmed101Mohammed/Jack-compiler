package parsing.jack_structure.class_vars;

import java.util.ArrayList;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.Type;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class ClassVarDec_C extends JackCommand{
    private ClassVarDec parent;
    private boolean complateStructured = false;
    // structure elements
    private ClassVariableType variableScope = null;
    private Token variableScopeToken;
    private Type type;
    public ArrayList<IdentifierToken> variablesNames = new ArrayList<>();
    private ArrayList<Token> commas = new ArrayList<>();
    private SymbolToken simicollon;


    public ClassVarDec_C(ClassVarDec parent)
    {
        this.parent = parent;
        this.getVariableScope();
        if(this.variableScope != null)
        {
            this.type = new Type();
            this.getAllVariablesNames();
            this.getSemiCollon();
        }
        else
        {
            CompilationEngine.decrementCurrentIndexByOne();
        }
    }

    public String generateXMLCode()
    {
        int varNamePointer = 0;
        int commaPointer = 0;
        String xmlCode = this.variableScopeToken.generateXMLCode() + "\n";
        xmlCode += this.type.generateXMlCode() + "\n";
        
        while(varNamePointer < this.variablesNames.size() - 1)
        {
            xmlCode += this.variablesNames.get(varNamePointer).generateXMLCode() + "\n";
            xmlCode += this.commas.get(commaPointer).generateXMLCode() + "\n";
            varNamePointer += 1;
            commaPointer += 1;
        }
        if(varNamePointer < this.variablesNames.size())
        {
            xmlCode += this.variablesNames.get(varNamePointer).generateXMLCode() + "\n";
        }
        
        xmlCode += this.simicollon.generateXMLCode();

        return xmlCode;
    }

    public void getSemiCollon()
	{
		Token token = CompilationEngine.advance();
		if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
		{
			this.simicollon	= SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.complateStructured = true;
            System.out.println("Success: Check semicolon.");
		}
        else
        {
            Token.allTokensErrors.add(new Error(
                                "UnExpectedToken", 
                                CompilationEngine.expectedRatharThanErrorMessage("';' SymbolToken", token.getBody()), 
                                token.getPosition()));
            System.out.println("Failed: Check semicolon.");

        }
	}

    public void getAllVariablesNames()
    {
        this.getVariableName();
        while (isAdvancedToComma()) 
        {
            getVariableName();     
        }
        CompilationEngine.decrementCurrentIndexByOne();
    }

    public boolean isAdvancedToComma()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(","))
        {
            this.commas.add(token);
            System.out.println("Success: Check comma.");
            return true;
        }
        return false;
    }

    public void getVariableName()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier && !isNameDublicateInDecScope(token.getBody()) && !this.parent.isNameDublicateeInClassScope(token.getBody()))
        {
            IdentifierToken varible = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.variablesNames.add(varible);
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnvalidVariableName", 
                                        "Varible name should be Identifier & Non dublicated in the same decleration statement or class scope.",
                                        token.getPosition()));
            System.out.println("Failed: Check variable name.");
        }
    }

    private boolean isNameDublicateInDecScope(String varName)
    {
        for(IdentifierToken i:this.variablesNames)
        {
            if(i.getBody().equals(varName))
            {
                return true;
            }
        }
        return false;
    }
    
    private void getVariableScope()
    {
        Token token = CompilationEngine.advance();
        String tokenBody = token.getBody();
        if(token.getType() == TokenType.Keyword && (tokenBody.equals("static") || tokenBody.equals("field")))
        {
            this.variableScope = getVariableScopeFromString(tokenBody);
            this.variableScopeToken = token;
            System.out.println("Success: Check class variable scope.");
        }
    }

    private ClassVariableType getVariableScopeFromString(String tokenBody)
    {
        ClassVariableType result = null;

        switch (tokenBody) {
            case "static":
                result = ClassVariableType.Static;
                break;
            case "field":
                result = ClassVariableType.Field;
                break;
            default:
                System.out.println("Filed: to detect the variable scope.");
                break;
        }

        return result;
    }

    public boolean getComplateStructured()
    {
        return this.complateStructured;
    }

    public ArrayList<IdentifierToken> getVariablesNames()
    {
        return this.variablesNames;
    }

    public boolean isVarDecNeedReviewForType()
    {
        return (this.type.getTokenType() == TokenType.Identifier);
    }

    public Type getType() {
        return type;
    }

    public String getVariableScopeTokenBodyString() {
        return variableScopeToken.getBody();
    }
}
