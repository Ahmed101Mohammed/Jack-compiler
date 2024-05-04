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
    public void generateVMCode() {
        // TODO Auto-generated method stub
        
    }    
}
