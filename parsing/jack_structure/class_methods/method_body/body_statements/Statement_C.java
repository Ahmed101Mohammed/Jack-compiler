package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;

public class Statement_C extends JackCommand
{// IStatement: Do Let If ...
    // isThereStatemen
    private IStatement statement;

    public Statement_C(Statements statements)
    {
        this.getStatement();
    }

    public String generateXMLCode()
    {
        String xmlCode = this.statement.generateXMLCode();
        return xmlCode;
    }
    
    private void getStatement()
    {
        Token token = CompilationEngine.advance();
        String tokenBody = token.getBody();

        switch(tokenBody)
        {
            case "let":
                CompilationEngine.decrementCurrentIndexByOne();
                LetStatement letStatement = new LetStatement();
                this.statement = letStatement;
                break;
            case "if":
                CompilationEngine.decrementCurrentIndexByOne();
                IfStatement ifStatement = new IfStatement();
                this.statement = ifStatement;
                break;
            case "while":
                CompilationEngine.decrementCurrentIndexByOne();
                whileStatement whileStatement = new whileStatement();
                this.statement = whileStatement;
                break;
            case "do":
                CompilationEngine.decrementCurrentIndexByOne();
                DoStatement doStatement = new DoStatement();
                this.statement = doStatement;
                break;
            case "return":
                CompilationEngine.decrementCurrentIndexByOne();
                ReturnStatement returnStatement = new ReturnStatement();
                this.statement = returnStatement;
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
    }
    
    public String generateVMCode()
    {
        String vmCode = this.statement.generateVMCode();
        return vmCode;
    }
}
