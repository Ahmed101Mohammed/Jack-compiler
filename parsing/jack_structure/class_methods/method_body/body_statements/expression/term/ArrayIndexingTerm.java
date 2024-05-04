package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class ArrayIndexingTerm implements Iterm
{
    private IdentifierToken varName;
    private SymbolToken leftSquareBracketSymbol;
    private Expression expression;
    private SymbolToken rightSquareBracketSymbol;

    public ArrayIndexingTerm()
    {
        this.getVarName();
        this.getLeftSquareBracket();
        this.expression = new Expression();
        this.getRightSquareBracket();
    }

    private void getRightSquareBracket()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("]"))
        {
            SymbolToken rightSquareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightSquareBracketSymbol = rightSquareBracket;
            System.out.println("Success: Check right square bracket."); 
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("']' SymbolToken", token.getBody()), token.getPosition()));
            System.out.println("Faile: Check right square braket.");
        }
    }

    private void getLeftSquareBracket()
    {
        Token token = CompilationEngine.advance();
        
        if(token.getType() == TokenType.Symbol && token.getBody().equals("["))
        {
            SymbolToken leftSquareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftSquareBracketSymbol = leftSquareBracket;
            System.out.println("Success: Check left square bracket.");
        }
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Identifier && 
        (Statements.subRoutineVars.isVarDublicatedInSubRoutineBody(token) || 
        SubRoutineDec.classVars.isNameDublicateeInClassScope(token.getBody())))
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
