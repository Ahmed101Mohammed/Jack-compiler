package tokens;

import java.util.HashMap;
import java.util.Map;

import helperClasses.*;

public class SymbolToken extends Token{
    private static Map<String, String> hashedSymbols = new HashMap<>(){{
        put(">", "&gt;");
        put("<", "&lt;");
        put("=", "=");
        put("+", "+");
        put("-", "-");
        put("*", "*");
        put("/", "/");
        put(";", ";");
        put(".", ".");
        put(",", ",");
        put("(", "(");
        put(")", ")");
        put("[", "[");
        put("]", "]");
        put("{", "{");
        put("}", "}");
        put("&", "&amp;");
        put("|", "|");
        put("~", "~");
    }};

    private SymbolToken(String token, Position tokePosition) 
    {
        super(token, tokePosition);
        this.setType(TokenType.Symbol);
    }

    public static SymbolToken createSymbolToken(String token, Position tokenPosition)
    {
        if(isSymbol(token))
        {
            SymbolToken newSymbol = new SymbolToken(token , tokenPosition);
            return newSymbol;
        }
        return null;
    }

    public static boolean isSymbol(String token)
    {
        if(hashedSymbols.get(token) != null)
        {
            return true;
        }
        return false;
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<symbol> " + hashedSymbols.get(this.tokenBody) + " </symbol>";
        return xmlCode;
    }
}
