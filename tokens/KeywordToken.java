package tokens;

import java.util.HashMap;
import java.util.Map;

import helperClasses.*;

public class KeywordToken extends Token
{
    private static Map<String, KeywordType> hashedKeywords = new HashMap<>(){{
        put("class", KeywordType.CLASS);
        put("method", KeywordType.METHOD);
        put("function", KeywordType.FUNCTION);
        put("constructor", KeywordType.CONSTRUCTOR);
        put("int", KeywordType.INT);
        put("boolean", KeywordType.BOOLEAN);
        put("char", KeywordType.CHAR);
        put("void", KeywordType.VOID);
        put("var", KeywordType.VAR);
        put("static", KeywordType.STATIC);
        put("field", KeywordType.FIELD);
        put("let", KeywordType.LET);
        put("do", KeywordType.DO);
        put("if", KeywordType.IF);
        put("else", KeywordType.ELSE);
        put("while", KeywordType.WHILE);
        put("return", KeywordType.RETURN);
        put("true", KeywordType.TRUE);
        put("false", KeywordType.FALSE);
        put("null", KeywordType.NULL);
        put("this", KeywordType.THIS);
    }};

    private KeywordToken(String token, Position tokenPosition) 
    {
        super(token, tokenPosition);
        this.setType(TokenType.Keyword);
    }

    public static KeywordToken createKeywordToken(String token, Position tokenPosition)
    {
        if(isKeywordToken(token))
        {
            KeywordToken newKeywordToken = new KeywordToken(token, tokenPosition);
            return newKeywordToken;
        }
        return null;
    }

    public static boolean isKeywordToken(String token)
    {
        if(hashedKeywords.get(token) != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<keyword> " + this.getBody() + " </keyword>";
        return xmlCode;
    }
}
