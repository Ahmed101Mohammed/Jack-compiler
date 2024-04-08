package tokens;

import java.util.ArrayList;

import helperClasses.*;
import helperClasses.Error;

public class Token {
    protected TokenType type;
    protected String tokenBody;
    protected Position tokenPosition;
    public static ArrayList<Error> allTokensErrors = new ArrayList<>(); 

    public Token(String token, Position tokenPosition)
    {
        this.tokenPosition = tokenPosition;
        this.tokenBody = token;
    }
    
    public void setType(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public String generateXMLCode()
    {
        return null;
    }
    
}