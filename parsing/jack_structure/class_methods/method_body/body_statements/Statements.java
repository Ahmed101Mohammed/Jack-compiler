package parsing.jack_structure.class_methods.method_body.body_statements;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_body.body_vars.VarDec;
import tokens.Token;
import tokens.TokenType;

public class Statements extends JackCommand 
{
    private ArrayList<Statement_C> statements = new ArrayList<>();
    // Subroutin vars
    static public VarDec subRoutineVars;

    public Statements(VarDec subRoutineVars)
    {
        this.subRoutineVars = subRoutineVars;
        while(this.isThereStatement())
        {
            Statement_C newStatement = new Statement_C();
            this.statements.add(newStatement);
        }
    }

    private boolean isThereStatement()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword)
        {
            return true;
        }
        CompilationEngine.decrementCurrentIndexByOne();
        return false;
    }
    
}
