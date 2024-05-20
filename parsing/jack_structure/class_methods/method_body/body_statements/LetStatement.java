package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.SubRoutineBody;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.IdentifierToken;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class LetStatement implements IStatement
{
    private KeywordToken letKeyword;
    private IdentifierToken varName;
    private SymbolToken leftSquareBracket = null;
    private Expression indexingExpression;
    private SymbolToken rightSquareBracket;
    private SymbolToken equalSign;
    private Expression expression;
    private SymbolToken simicolon;
    
    public LetStatement()
    {
        this.getLetKeyword();
        this.getVarName();
        this.getLeftSquareBracket();
        if(this.leftSquareBracket != null)
        {
            this.indexingExpression = new Expression();
            this.getRightSquareBracket();
        }
        this.getEqualSign();
        this.expression = new Expression();
        this.getSimiColon();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<letStatement>\n";
        xmlCode += this.letKeyword.generateXMLCode() + "\n";
        xmlCode += this.varName.generateXMLCode() + "\n";
        if(this.leftSquareBracket != null)
        {
            xmlCode += this.leftSquareBracket.generateXMLCode() + "\n";
            xmlCode += this.indexingExpression.generateXMLCode() + "\n";
            xmlCode += this.rightSquareBracket.generateXMLCode() + "\n";
        }
        xmlCode += this.equalSign.generateXMLCode() + "\n";
        xmlCode += this.expression.generateXMLCode() + "\n";
        xmlCode += this.simicolon.generateXMLCode() + "\n";
        xmlCode += "</letStatement>";
        return xmlCode;
    }

    private void getSimiColon()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
        {
            SymbolToken simiColon = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.simicolon = simiColon;
            System.out.println("Success: Check simicolon.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("';' SymbolToken", token.getBody()),
                token.getPosition()));
            
            System.out.println("Fail: Check simicolon. the exist token is: " + token.getBody());
        }
    }

    private void getEqualSign()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("="))
        {
            SymbolToken equal = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.equalSign = equal;
            System.out.println("Success: Check equal sign.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedError",
                CompilationEngine.expectedRatharThanErrorMessage("'=' SymbolToken", token.getBody()), token.getPosition()));
            System.out.println("Faile: Check equal sign.");
        }
    }
    private void getRightSquareBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("]"))
        {
            SymbolToken squareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightSquareBracket = squareBracket;
            System.out.println("Success: Check right square bracket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("']' SymbolToken", token.getBody()), 
                token.getPosition()));
            System.out.println("Faile: Check right square bracket.");
        }
    }

    private void getLeftSquareBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("["))
        {
            SymbolToken squareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftSquareBracket = squareBracket;
            System.out.println("Success: Check left square bracket.");
        }
        else
        {
            CompilationEngine.decrementCurrentIndexByOne();
        }
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();
        IdentifierToken variable = IdentifierToken.createIdentifierToken( token.getBody(), token.getPosition());
        if(token.getType() == TokenType.Identifier && 
        (Statements.subRoutineVars.isVarDublicatedInSubRoutineBody(token) || 
        SubRoutineDec.classVars.isNameDublicateeInClassScope(token.getBody()) ||
        SubRoutineBody.parameters.isIdentifierDublicated(variable)))
        {
            IdentifierToken var = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.varName = var;
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("[varName] IdentifieryToken", token.getBody()),
                token.getPosition()));
            System.out.println("Faile: Check variable name.");
        }
    }

    private void getLetKeyword()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("let"))
        {
            KeywordToken let = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.letKeyword = let;
            System.out.println("Success: Check let keyword.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("'let' keyword", token.getBody()), token.getPosition()));
            System.out.println("Failed: Check let keyword.");
        }
    }

    @Override
    public String generateVMCode() {
        // TODO Auto-generated method stub
        return null;
    }    
}
