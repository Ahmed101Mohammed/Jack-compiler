package parsing.jack_structure.class_methods.method_parameter;

import java.lang.foreign.SymbolLookup;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.Type;
import tokens.IdentifierToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.SymboleTable;

public class Parameter_C extends JackCommand
{
    private Type type;
    private IdentifierToken name;
    // Other members
    private ParameterList parent;

    public Parameter_C(ParameterList parameterList)
    {
        this.parent = parameterList;
        this.type = new Type();
        this.getName();
    }

    public String generateXMLCode()
    {
        String xmlCode = type.generateXMlCode() + "\n";
        xmlCode += this.name.generateXMLCode();
        return xmlCode;
    }

    public String getName()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier && !isDublicatedName(token))
        {
            IdentifierToken name = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.name = name;
            System.out.println("Success: Check parameter name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnValidParameterName", 
                                "Jack parameter name should be Identifier and not dublicated name, so this token '" + token.getBody() + "' is invalid.", 
                                token.getPosition()));
            System.out.println("Faile: Check parameter name.");
        }
        return null;
    }

    private boolean isDublicatedName(Token token)
    {
        for(Parameter_C p:this.parent.parameters)
        {
            if(p.getPureName().equals(token.getBody()))
            {
                return true;
            }
        }

        return false;
    }

    public String getPureName()
    {
        return this.name.getBody();
    }

    public boolean isNeededToReviewType()
    {
        return (this.type.getTokenType() == TokenType.Identifier);
    }

    @Override
    public String generateVMCode() {
        SymboleTable.addVAr(getPureName(), this.type.getBody(), "argument");
        return "";
    }
}
