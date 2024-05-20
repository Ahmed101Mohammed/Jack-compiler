package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import tokens.IdentifierToken;
import tokens.Token;
import tokens.TokenType;

public class VariableNameTerm implements Iterm
{
    private IdentifierToken varName;

    public VariableNameTerm()
    {
        this.getVarName();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = this.varName.generateXMLCode();
        return xmlCode;
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Identifier)
        {
            IdentifierToken var = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.varName = var;
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InvalidVariable", 
                "To use variable in Jack you should declared it.", token.getPosition()));
            System.out.println("Faile: Check variable name.");
        }
    }

    @Override
    public void generateVMCode() {
        // TODO Auto-generated method stub
        
    }    
}
