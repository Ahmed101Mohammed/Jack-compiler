package parsing.jack_structure.class_methods.method_parameter;

import java.util.ArrayList;

import parsing.CompilationEngine;
import tokens.Token;
import tokens.TokenType;

public class ParameterList {
    public ArrayList<Parameter_C> parameters = new ArrayList<>();
    
    public ParameterList()
    {
        if(!this.isThereParameters())
        {
            return;
        }

        do
        {
            Parameter_C oneParameter = new Parameter_C(this);
            this.parameters.add(oneParameter);
            if(oneParameter.isNeededToReviewType())
            {
                CompilationEngine.neededReviewParameter.add(oneParameter);
            }
            
        }while(this.isThereComma());
    }

    private boolean isThereComma()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(","))
        {
            return true;
        }

        CompilationEngine.decrementCurrentIndexByOne();
        return false;
    }

    private boolean isThereParameters()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            return false;
        }

        return true;
    }
}
