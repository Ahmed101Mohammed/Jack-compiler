package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import parsing.CompilationEngine;
import tokens.StringConstantToken;
import tokens.Token;
import tokens.TokenType;

public class StringConstantTerm implements Iterm 
{
    private StringConstantToken stringConstant;

    public StringConstantTerm()
    {
        this.getStringConstant();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = this.stringConstant.generateXMLCode();
        return xmlCode;
    }

    private void getStringConstant()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.StringConstant)
        {
            StringConstantToken string = StringConstantToken.createStringConstantToken(token.getBody(), token.getPosition());
            this.stringConstant = string;
            System.out.println("Success: Check String constant term.");
        }
    }
    @Override
    public String generateVMCode() 
    {
        String stringConstant = this.stringConstant.getBody();
        int stringConstantLength = stringConstant.length();
        String vmCode = "push constant " + stringConstantLength + "\n" +  "String.new 1\n";
        for(char c:stringConstant.toCharArray())
        {
            int intC = (int) c;
            vmCode += "push constant " + intC;
            vmCode += "call String.appendChar 1";
        }  
        return null;
    }    
}
