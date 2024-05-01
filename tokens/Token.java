package tokens;

import java.util.ArrayList;

import helperClasses.*;
import helperClasses.Error;

public class Token {
    protected TokenType type;
    protected String body;
    protected Position position;
    public static ArrayList<Error> allTokensErrors = new ArrayList<>(); 

    public Token(String token, Position tokenPosition)
    {
        this.position = tokenPosition;
        this.body = token;
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

	public String getBody()
	{
		return this.body;
	}

    public Position getPosition()
    {
        return position;
    }
    
}
