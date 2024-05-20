package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class whileStatement implements IStatement
{
    // 'while' '('expression')' '{'statements'}'
    private KeywordToken whileKeyword;
    private SymbolToken leftParanthes;
    private Expression expression;
    private SymbolToken rightParanthes;
    private SymbolToken leftCurlyBraket;
    private Statements statements;
    private SymbolToken rightCurlyBraket;

    public whileStatement()
    {
        this.getWhileKeyword();
        this.getLeftPananthes();
        this.expression = new Expression();
        this.getRightParanthes();
        this.getLeftCurlyBraket();
        this.statements = new Statements(Statements.subRoutineVars);
        this.getRightCurlyBraket();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<whileStatement>\n";
        xmlCode += this.whileKeyword.generateXMLCode() + "\n";
        xmlCode += this.leftParanthes.generateXMLCode() + "\n";
        xmlCode += this.expression.generateXMLCode() + "\n";
        xmlCode += this.rightParanthes.generateXMLCode() + "\n";
        xmlCode += this.leftCurlyBraket.generateXMLCode() + "\n";
        xmlCode += this.statements.generateXMLCode() + "\n";
        xmlCode += this.rightCurlyBraket.generateXMLCode() + "\n";
        xmlCode += "</whileStatement>";
        return xmlCode;
    }

    private void getWhileKeyword()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Keyword && token.getBody().equals("while"))
        {
            KeywordToken while_ = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.whileKeyword = while_;
            System.out.println("Success: Check while keyword.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedTokne", 
            CompilationEngine.expectedRatharThanErrorMessage("'while' keywordToken", token.getBody()), 
            token.getPosition()));
            System.out.println("Fail: Check while keyword.");
        }
    }

    private void getLeftPananthes()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("("))
        {
            SymbolToken leftParanthes_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftParanthes = leftParanthes_;
            System.out.println("Success: Check left paranthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
            CompilationEngine.expectedRatharThanErrorMessage("'(' SymbolToken", token.getBody()), 
            token.getPosition()));

            System.out.println("Faile: Check left paranthes.");
        }
    }

    private void getLeftCurlyBraket()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
        {
            SymbolToken leftCurlyBraket_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftCurlyBraket = leftCurlyBraket_;
            System.out.println("Success: Check left curly braket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
            CompilationEngine.expectedRatharThanErrorMessage("'{' SymbolToken", token.getBody()), 
            token.getPosition()));

            System.out.println("Faile: Check left curly brakte.");
        }
    }

    private void getRightCurlyBraket()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("}"))
        {
            SymbolToken rightCurlyBraket_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightCurlyBraket = rightCurlyBraket_;
            System.out.println("Success: Check right curly braket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
            CompilationEngine.expectedRatharThanErrorMessage("'}' SymbolToken", token.getBody()), 
            token.getPosition()));

            System.out.println("Faile: Check right curly brakte.");
        }
    }

    private void getRightParanthes()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            SymbolToken rightParanthes_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightParanthes = rightParanthes_;
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
    public String generateVMCode() {
        // TODO Auto-generated method stub
        return null;
    }    
}
