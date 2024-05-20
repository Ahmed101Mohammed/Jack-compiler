package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class IfStatement implements IStatement
{
    private KeywordToken ifKeyWord;
    private SymbolToken lefetParanthes;
    private Expression expression;
    private SymbolToken rightParanthes;
    private SymbolToken leftCurlybaraket;
    private Statements statements;
    private SymbolToken rightCurlyBraket;
    private KeywordToken elseKeyword = null;
    private SymbolToken leftCurlybaraket_;
    private Statements statements_;
    private SymbolToken rightCurlyBraket_;

    public IfStatement()
    {
        this.getIfKeyword();
        this.getLeftParnathes();
        this.expression = new Expression();
        this.getRightParnathes();
        this.getLeftCurlybraket();
        this.statements = new Statements(Statements.subRoutineVars);
        this.getRightCurlyBraket();
        this.getElseKeyword(); //  
        if(this.elseKeyword != null)
        {
            this.getLeftCurlybraket_();
            this.statements_ = new Statements(Statements.subRoutineVars);
            this.getRightCurlyBraket_();
        }
        else
        {
            CompilationEngine.decrementCurrentIndexByOne();
        }
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<ifStatement>\n";
        xmlCode += this.ifKeyWord.generateXMLCode() + '\n';
        xmlCode += this.lefetParanthes.generateXMLCode() + '\n';
        xmlCode += this.expression.generateXMLCode() + '\n';
        xmlCode += this.rightParanthes.generateXMLCode() + '\n';
        xmlCode += this.leftCurlybaraket.generateXMLCode() + '\n';
        xmlCode += this.statements.generateXMLCode() + '\n';
        xmlCode += this.rightCurlyBraket.generateXMLCode() + '\n';
        if(this.elseKeyword != null)
        {
            xmlCode += this.elseKeyword.generateXMLCode() + '\n';
            xmlCode += this.leftCurlybaraket_.generateXMLCode() + '\n';
            xmlCode += this.statements_.generateXMLCode() + '\n';
            xmlCode += this.rightCurlyBraket_.generateXMLCode() + '\n';
        }
        xmlCode += "</ifStatement>";
        return xmlCode;
    }

    private void getIfKeyword()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Keyword && token.getBody().equals("if"))
        {
            KeywordToken if_ = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.ifKeyWord = if_;
            System.out.println("Success: Check if keyword.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedTokne", 
            CompilationEngine.expectedRatharThanErrorMessage("'if' keywordToken", token.getBody()), 
            token.getPosition()));
            System.out.println("Fail: Check if keyword.");
        }
    }

    private void getElseKeyword()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Keyword && token.getBody().equals("else"))
        {
            KeywordToken else_ = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.elseKeyword = else_;
            System.out.println("Success: Check else keyword.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedTokne", 
            CompilationEngine.expectedRatharThanErrorMessage("'else' keywordToken", token.getBody()), 
            token.getPosition()));
            System.out.println("Nooo: Check else keyword.");
        }
    }

    private void getLeftParnathes()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("("))
        {
            SymbolToken leftParanthes_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.lefetParanthes = leftParanthes_;
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

    private void getLeftCurlybraket()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
        {
            SymbolToken leftCurlyBraket_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftCurlybaraket = leftCurlyBraket_;
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

    private void getLeftCurlybraket_()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
        {
            SymbolToken leftCurlyBraket_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftCurlybaraket_ = leftCurlyBraket_;
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

    private void getRightCurlyBraket_()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("}"))
        {
            SymbolToken rightCurlyBraket__ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightCurlyBraket_ = rightCurlyBraket__;
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

    private void getRightParnathes()
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

