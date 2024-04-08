package tokens;

import java.util.HashMap;
import java.util.Map;

import helperClasses.*;
import helperClasses.Error;

public class IdentifierToken extends Token
{
    private static Map<String, Integer> hashedNumbers = new HashMap<>(){{
        for (char c = '0'; c <= '9'; c++) {
            put(String.valueOf(c), 0);
        }
    }};

    private static Map<String, Integer> hashedValiedChars = new HashMap<>(){{
        for (char c = 'a'; c <= 'z'; c++) {
            put(String.valueOf(c), 0);
        }

        // Add uppercase letters from 'A' to 'Z'
        for (char c = 'A'; c <= 'Z'; c++) {
            put(String.valueOf(c), 0);
        }

        // Add digits from '0' to '9'
        for (char c = '0'; c <= '9'; c++) {
            put(String.valueOf(c), 0);
        }

        // Add underscore character '_'
        put("_", 0);
    }};

    private IdentifierToken(String token, Position tokePosition)    
    {
        super(token, tokePosition);
        this.setType(TokenType.Identifier);
    }

    public static IdentifierToken createIdentifierToken(String token, Position position)
    {
        if(isIdentifierToken(token, position))
        {
            IdentifierToken newIdentifierToken = new IdentifierToken(token, position);
            return newIdentifierToken;
        }
        return null;
    }

    public static boolean isIdentifierToken(String token, Position tokePosition)
    {
        if(!IntegerConstantToken.isIntegerConstantToken(token, tokePosition) && !KeywordToken.isKeywordToken(token))
        {
            if(hashedNumbers.get(token.toCharArray()[0]+"") != null)
            {
                Token.allTokensErrors.add(new Error("False Identifier", "Jack identifier does not start with integer.", tokePosition));
                return false;
            }
            
            for(char c:token.toCharArray())
            {
                if(hashedValiedChars.get(c+"") == null)
                {
                    Token.allTokensErrors.add(new Error("False Identifier", 
                            "Your identifier ("+ token + ") have '"+ c +"' char, language just support english char,number and underscore for naming identifiers.", 
                            tokePosition));
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String generateXMLCode()
    {
        String xmlCode = "<identifier> " + this.tokenBody + " </identifier>";
        return xmlCode;
    }
}
