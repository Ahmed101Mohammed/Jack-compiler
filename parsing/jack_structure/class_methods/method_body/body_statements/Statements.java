package parsing.jack_structure.class_methods.method_body.body_statements;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.SubRoutineBody;
import parsing.jack_structure.class_methods.method_body.body_vars.VarDec;
import parsing.jack_structure.class_methods.method_parameter.ParameterList;
import parsing.jack_structure.class_vars.ClassVarDec;
import tokens.IdentifierToken;
import tokens.Token;
import tokens.TokenType;

public class Statements extends JackCommand 
{
    private ArrayList<Statement_C> allStatements = new ArrayList<>();
    // Subroutin vars
    static public VarDec subRoutineVars;

    public Statements(VarDec subRoutineVars)
    {
        this.subRoutineVars = subRoutineVars;
        while(this.isThereStatement())
        {
            Statement_C newStatement = new Statement_C(this);
            this.allStatements.add(newStatement);
        }
    }

    public String generateXMLCode()
    {
        String xmlCode = "<statements>\n";
        for(Statement_C statement: this.allStatements)
        {
            xmlCode += statement.generateXMLCode() + "\n";
        }
        xmlCode += "</statements>";
        return xmlCode;
    }
    
    private boolean isThereStatement()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        System.out.println("There is more statement finding: " + token.getBody());
        if(token.getType() == TokenType.Keyword)
        {
            return true;
        }
        return false;
    }
    
}
