package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class ReturnStatement implements IStatement 
{
    private KeywordToken returnKeyword;
    private Expression expression;
    private SymbolToken simicolon;

    public ReturnStatement()
    {
        this.getReturnKeyword();
        if(!this.isThereSimiColon())
        {
            this.expression = new Expression();
        }
        this.getSimiColon();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<returnStatement>\n";
        xmlCode += this.returnKeyword.generateXMLCode() + "\n";
        if(this.expression != null)
        {
            xmlCode += this.expression.generateXMLCode() + "\n";
        }
        xmlCode += this.simicolon.generateXMLCode() + "\n";
        xmlCode += "</returnStatement>";
        return xmlCode;
    }

    private void getReturnKeyword()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("return"))
        {
            KeywordToken return_ = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.returnKeyword = return_;
            System.out.println("Success: Check return keyword.");
        }
        else
        {
            System.out.println("Fail: Check return keyword.");
        }
    }

    private boolean isThereSimiColon()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
        {
            return true;
        }
        return false;
    }

    private void getSimiColon()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
        {
            SymbolToken simicolon_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.simicolon = simicolon_;
            System.out.println("Success: Check simicolon.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedError", 
                CompilationEngine.expectedRatharThanErrorMessage("';' SymbolToken", token.getBody()),
                token.getPosition()));
            System.out.println("Fail: Check simicolon.");
        }
    }
    @Override
    public String generateVMCode() {
        // TODO Auto-generated method stub
        return null;
    }    
}
