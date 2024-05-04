package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import java.beans.Expression;
import java.lang.foreign.SymbolLookup;

import helperClasses.Error;
import parsing.CompilationEngine;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class PeriorityTerm implements Iterm
{
    private SymbolToken leftParanthesToken;
    private parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression expression;
    private SymbolToken rightParanthesToken;

    public PeriorityTerm()
    {
        this.getLeftParanthes();
        this.expression = new parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression();
        this.getRightParanthes();
    }

    private void getLeftParanthes()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("("))
        {
            SymbolToken leftParanthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftParanthesToken = leftParanthes;
            System.out.println("Success: Check left paranthes.");
        }
    }

    private void getRightParanthes()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            SymbolToken rightParanthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightParanthesToken = rightParanthes;
            System.out.println("Success: Check right paranthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("')' SymbolToken", token.getBody()), 
            token.getPosition()));

            System.out.println("Faile: Check right paranthes.");
        }
    }

    @Override
    public void generateVMCode() {
        // TODO Auto-generated method stub
        
    }    
}
