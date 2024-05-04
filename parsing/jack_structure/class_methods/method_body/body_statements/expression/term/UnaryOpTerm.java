package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import parsing.CompilationEngine;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class UnaryOpTerm implements Iterm
{
    private SymbolToken unaryOperator;
    private Term term;

    public UnaryOpTerm()
    {
        this.getUnaryOperator();
        this.term = new Term();
    }

    private void getUnaryOperator()
    {
        Token token = CompilationEngine.advance();
        String tokenBody = token.getBody();

        if(token.getType() == TokenType.Symbol && 
            (tokenBody.equals("~") || tokenBody.equals("-")))
        {
            SymbolToken unaryop = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.unaryOperator = unaryop;
            System.out.println("Success: Check unary operator term.");
        }
    }
    @Override
    public void generateVMCode() {
        // TODO Auto-generated method stub
        
    }    
}
