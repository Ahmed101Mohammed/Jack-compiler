package parsing.jack_structure.class_methods.method_body.body_statements.expression.op;

import java.util.HashMap;

import parsing.CompilationEngine;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class Op 
{
    private SymbolToken op;
    private boolean isValidOp = false;
    private HashMap<String, String> opToVM = new HashMap<>() {{
        put("+", "add\n");
        put("-", "sub\n");
        put("*", "Math.multiply\n");
        put("/", "Math.divide\n");
        put("&", "and\n");
        put("|", "or\n");
        put("=", "eq\n");
        put("<", "lt\n");
        put(">", "gt\n");
    }}; 
    
    public Op()
    {
        this.getOp();
    }

    public String generateXMLCode()
    {
        String xmlCode = this.op.generateXMLCode();
        return xmlCode;
    }

    private void getOp()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && isOperationSymbol(token.getBody()))
        {
            SymbolToken op_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.op = op_;
            this.isValidOp = true;
            System.out.println("Success: Check op in expression.");
        }
        else
        {
            CompilationEngine.decrementCurrentIndexByOne();
        }
    }

    private boolean isOperationSymbol(String tokenBody)
    {
        String[] ops = {"+", "-", "/", "*", ">", "<", "=", "|", "&"};

        for(String op: ops)
        {
            if(tokenBody.equals(op))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isValidOp()
    {
        return this.isValidOp;
    }

    public String generateVMCode()
    {
        String opString = op.getBody();
        String vmCode = opToVM.get(opString);

        return vmCode;
    }
}
