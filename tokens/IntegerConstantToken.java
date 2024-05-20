package tokens;

import helperClasses.*;
import helperClasses.Error;

public class IntegerConstantToken extends Token{
    private IntegerConstantToken(String token, Position tokenPosition)
    {
        super(token, tokenPosition);
        this.setType(TokenType.IntegeralConstant);
    }

    public static IntegerConstantToken createIntegerConstantToken(String token, Position tokenPosition)
    {
        if(isIntegerConstantToken(token, tokenPosition))
        {
            IntegerConstantToken newIntegerConstantToken = new IntegerConstantToken(token, tokenPosition);
            return newIntegerConstantToken;
        }
        return null;
    }

    public static boolean isIntegerConstantToken(String token, Position tokenPosition)
    {
        try
        {
            int integerConstant = Integer.parseInt(token);
            if(token.split(".").length > 1)
            {
                Token.allTokensErrors.add(new Error("unSupportedNumber", "Jack does not supported float numbers.", tokenPosition));
                return false;
            }
            else if(integerConstant > 32767)
            {
                Token.allTokensErrors.add(new Error("UnSupportedNumber", "Jack does not supported integers greated than 32767", tokenPosition));
                return false;
            }
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<integerConstant> " + this.getBody() + " </integerConstant>";
        return xmlCode;
    }
    
}
