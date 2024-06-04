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

    @Override
    public String generateXMLCode() {
        String xmlCode = this.unaryOperator.generateXMLCode() + "\n";
        xmlCode += this.term.generateXMLCode();
        return xmlCode;
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
        else
        {
            System.out.println("Fail: Check unary operator term.");
        }
    }
    @Override
    public String generateVMCode() {
        String vmCode = this.term.generateVMCode();
        switch(this.unaryOperator.getBody())
        {
            case "-":
                vmCode += "neg\n";
                break;
            case "~":
                vmCode += "not\n";
                break;
        }
        return vmCode;
    }    
}
