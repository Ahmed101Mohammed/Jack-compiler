package parsing.jack_structure.class_methods.method_body;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_body.body_vars.VarDec;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class SubRoutineBody extends JackCommand
{
    private SymbolToken LeftCurlyBracket;
    private VarDec varDec;
    private Statements statement;
    private SymbolToken RightCurlyBracket;

    public SubRoutineBody()
    {
        this.getLeftCurlyBracket();
        // Get vars
        // Get statements
        this.getRightCurlyBracket();
    }

    public void getLeftCurlyBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
        {
            SymbolToken leftCurlyBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.LeftCurlyBracket = leftCurlyBracket;
            System.out.println("Success: Check left curly bracket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                                        CompilationEngine.expectedRatharThanErrorMessage("'{' symbol token", token.getBody()), 
                                        token.getPosition()));
            System.out.println("Faile: Check left curly bracket.");
        }
    }

    public void getRightCurlyBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("}"))
        {
            SymbolToken rightCurlyBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.RightCurlyBracket = rightCurlyBracket;
            System.out.println("Success: Check right curly bracket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                                        CompilationEngine.expectedRatharThanErrorMessage("'{' symbol token", token.getBody()), 
                                        token.getPosition()));
            System.out.println("Faile: Check right curly bracket.");
        }
    }   
}
