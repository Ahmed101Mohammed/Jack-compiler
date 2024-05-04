package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import parsing.CompilationEngine;
import tokens.Token;
import tokens.TokenType;

public class Term 
{
    // ITerm
    private Iterm term;

    public Term()
    {
        this.setTerm();
    }

    private void setTerm()
    {
        Token token = CompilationEngine.advance();
        TokenType tokenType = token.getType();

        switch(tokenType)
        {
            case IntegeralConstant:
                CompilationEngine.decrementCurrentIndexByOne();
                this.term = new IntegerConstantTerm();
                break;

            case StringConstant:
                CompilationEngine.decrementCurrentIndexByOne();
                this.term = new StringConstantTerm();
                break;

            case Keyword:
                CompilationEngine.decrementCurrentIndexByOne();
                this.term = new KeywordConstantTerm();
                break;

            case Symbol:
                CompilationEngine.decrementCurrentIndexByOne();
                if(token.getBody().equals("("))
                {
                    this.term = new PeriorityTerm();
                }
                else
                {
                    this.term = new UnaryOpTerm();
                }
                break;

            case Identifier:
                Token nextToken = CompilationEngine.advance();
                CompilationEngine.decrementCurrentIndexByOne();
                CompilationEngine.decrementCurrentIndexByOne();

                if(nextToken.getBody().equals("["))
                {
                    this.term = new ArrayIndexingTerm();
                }
                else if(nextToken.getBody().equals(";"))
                {
                    this.term = new VariableNameTerm();
                }
                else
                {
                    // this.term = new SuberRoutineCallTerm();
                }
                    
                break;
            default:
                System.out.println("Error: Failed to define the term; because of the tokenType maybe is null.");
                break;

        }
        
    }
}
