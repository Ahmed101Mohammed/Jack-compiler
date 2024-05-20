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

    public String generateXMLCode()
    {
        String xmlCode = "<term>\n";
        xmlCode += this.term.generateXMLCode() + "\n";
        xmlCode += "</term>";
        return xmlCode;
    }

    private void setTerm()
    {
        Token token = CompilationEngine.advance();
        // System.out.println("Token body in term class: " + token.getBody() +" "+ token.getType());
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
                    System.out.println("// ExpectUnary: " + token.getBody());
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
                else if(nextToken.getBody().equals(".") || nextToken.getBody().equals("("))
                {
                    this.term = new SubRoutineCallTerm();
                }
                else
                {
                    this.term = new VariableNameTerm();
                }
                    
                break;
            default:
                System.out.println("Error: Failed to define the term; because of the tokenType maybe is null.");
                break;

        }
        
    }
}
