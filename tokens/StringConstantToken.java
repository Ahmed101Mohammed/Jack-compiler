package tokens;

import helperClasses.*;

public class StringConstantToken extends Token{
    private StringConstantToken(String token, Position tokenPosition)
    {
        super(token, tokenPosition);
        this.setType(TokenType.StringConstant);
    }

    public static StringConstantToken createStringConstantToken(String token, Position tokenPosition)
    {
        if(isStringConstantToken(token))
        {
            StringConstantToken newStringConstantToken = new StringConstantToken(token, tokenPosition);
            return newStringConstantToken;
        }
        return null;
    }

    public static boolean isStringConstantToken(String token)
    {
        char[] charsOfToken = token.toCharArray();
        if(charsOfToken[0] == '"' && charsOfToken[charsOfToken.length-1] == '"')
        {
            return true;
        }
        return false;
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<stringConstant> " + this.getStringBody() + " </stringConstant>";
        return xmlCode;
    }

    private String getStringBody()
    {
        String stringBody = "";
        char[] string = this.getBody().toCharArray();
        for(int i = 1; i < string.length-1; i++)
        {
            stringBody += string[i];
        }

        return stringBody;
    }
}
