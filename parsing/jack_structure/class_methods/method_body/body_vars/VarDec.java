package parsing.jack_structure.class_methods.method_body.body_vars;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;
import tokens.TokenType;

public class VarDec extends JackCommand
{
    private ArrayList<VarDec_C> vars = new ArrayList<>();
    
    public VarDec()
    {
        while (this.thereIsVarKeyWord()) 
        {
            VarDec_C newOne = new VarDec_C(this);
            vars.add(newOne);     
            // if needed to review added to compilationEngine.someArrayList.add(newOne)
        }
    }

    private boolean thereIsVarKeyWord()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("var"))
        {
            return true;
        }
        CompilationEngine.decrementCurrentIndexByOne();
        return false;
    }
}
