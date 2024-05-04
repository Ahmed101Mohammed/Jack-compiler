package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;

public class Statement_C extends JackCommand
{// IStatement: Do Let If ...
    // isThereStatemen
    private IStatement statement;

    public Statement_C()
    {
        this.statement = this.getStatement();
    }
    
    private IStatement getStatement()
    {
        Token token = CompilationEngine.advance();
        String tokenBody = token.getBody();
        IStatement statement = null;

        switch(tokenBody)
        {
            case "let":
                CompilationEngine.decrementCurrentIndexByOne();
                // 
                break;
            case "if":
                CompilationEngine.decrementCurrentIndexByOne();
                break;
            case "while":
                CompilationEngine.decrementCurrentIndexByOne();
                break;
            case "do":
                CompilationEngine.decrementCurrentIndexByOne();
                break;
            case "return":
                CompilationEngine.decrementCurrentIndexByOne();
                break;
            default:
                Token.allTokensErrors.add(new Error("InValidStatement", 
                        "Jack language support jsut theste statements in subroutine body: let|if|while|do|return. the '" + 
                        tokenBody + 
                        "' is not supported statement in Jack subroutin body.", 
                        token.getPosition()));
                System.out.println("Failed: Check Statement in subroutin body.");
                break;
        }

        return statement;
    }
    
}
