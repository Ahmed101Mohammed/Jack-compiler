package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import helperClasses.Error;
import parsing.CompilationEngine;
import tokens.Token;
import tokens.TokenType;

public class SubRoutineCallTerm implements Iterm
{
    private IsubRoutineCallTerm subRoutineCallTerm;

    public SubRoutineCallTerm()
    {
        this.getSubRoutineCallTerm();    
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = this.subRoutineCallTerm.generateXMLCode();
        return xmlCode;
    }

    private void getSubRoutineCallTerm()
    {
        Token token = CompilationEngine.advance();
        Token token2 = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        CompilationEngine.decrementCurrentIndexByOne();

        if(token2.getType() == TokenType.Symbol && token2.getBody().equals("("))
        {
            this.subRoutineCallTerm = new NormalSubRoutineCallTerm();
        }
        else if(token2.getType() == TokenType.Symbol && token2.getBody().equals("."))
        {
            this.subRoutineCallTerm = new MethodSubRoutineCallTerm();
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
                CompilationEngine.expectedRatharThanErrorMessage("'(' Or '.' SymbolToken", token2.getBody()), 
                token.getPosition()));

            System.out.println("Faile: Check subRoutine call term.");
        }
    }

    @Override
    public String generateVMCode() {
        String vmCode = this.subRoutineCallTerm.generateVMCode();
        return vmCode;
        
    }    
}
