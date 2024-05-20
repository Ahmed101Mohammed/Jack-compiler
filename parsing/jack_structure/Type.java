package parsing.jack_structure;

import helperClasses.Error;
import helperClasses.Position;
import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;
import tokens.TokenType;

public class Type extends JackCommand
{
    protected String body;
    protected TokenType tokenType;
    protected Position position;
    private Token typeToken;

    public Type()    
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier)
        {
            this.body = token.getBody();
            this.tokenType = token.getType();
            this.position = token.getPosition();
            this.typeToken = token;
            this.successCheckingMessage();
        }
        else if(isKeyWordDataType(token))
        {
            this.body = token.getBody();
            this.tokenType = TokenType.Keyword;
            this.position = token.getPosition();
            this.typeToken = token;
            this.successCheckingMessage();
        }
        else
        {
            Token.allTokensErrors.add(new Error("InValidType", 
                                "There is unvalid type data, this token '"+ token.getBody() +"' is not a valid data type in Jack",
                                token.getPosition()));
            System.out.println("Failed: check varibale|parameter type.");
        }
    }

    public String generateXMlCode()
    {
        String xmlCode = this.typeToken.generateXMLCode();
        return xmlCode;
    }
    public boolean isKeyWordDataType(Token token)
    {
        String tokenBody = token.getBody();
        if(token.getType() == TokenType.Keyword && (tokenBody.equals("int") || tokenBody.equals("char") || tokenBody.equals("boolean")))
        {
            return true;
        }
        return false;
    }

    public String getBody() {
        return body;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void successCheckingMessage()
    {
        System.out.println("Success: Check variable|parameter type.");
    }

    public Position getPosition() {
        return position;
    }
}
