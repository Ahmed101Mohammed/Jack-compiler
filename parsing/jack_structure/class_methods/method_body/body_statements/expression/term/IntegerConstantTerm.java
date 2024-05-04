package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import parsing.CompilationEngine;
import tokens.IntegerConstantToken;
import tokens.Token;
import tokens.TokenType;

public class IntegerConstantTerm implements Iterm
{
    private IntegerConstantToken integerConstant;

    public IntegerConstantTerm()
    {
        this.getIntegerConstant();
    }

    private void getIntegerConstant()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.IntegeralConstant)
        {
            IntegerConstantToken integer = IntegerConstantToken.createIntegerConstantToken(token.getBody(), token.getPosition());
            this.integerConstant = integer;
            System.out.println("Success: Check Integer Constant term.");
        }
    }

    @Override
    public void generateVMCode() {
        // TODO Auto-generated method stub
    }    
}
