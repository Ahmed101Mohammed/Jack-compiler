package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import parsing.CompilationEngine;
import tokens.KeywordToken;
import tokens.Token;
import tokens.TokenType;

public class KeywordConstantTerm implements Iterm 
{
    private KeywordToken keywordConstant;

    public KeywordConstantTerm()
    {
        this.getKeywordConstant();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = this.keywordConstant.generateXMLCode();
        return xmlCode;
    }
    private void getKeywordConstant()
    {
        Token token = CompilationEngine.advance();
        String tokenBody = token.getBody();

        if(token.getType() == TokenType.Keyword && 
                (tokenBody.equals("false") || 
                tokenBody.equals("true") || 
                tokenBody.equals("null") || 
                tokenBody.equals("this")))
        {
            KeywordToken keyword = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.keywordConstant = keyword;
            System.out.println("Success: Check keyword constant term.");
        }
    }
    @Override
    public void generateVMCode() {
        // TODO Auto-generated method stub
        
    }    
}
