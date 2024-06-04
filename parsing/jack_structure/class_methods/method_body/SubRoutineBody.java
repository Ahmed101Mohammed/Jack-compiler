package parsing.jack_structure.class_methods.method_body;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import parsing.jack_structure.class_methods.method_body.body_vars.VarDec;
import parsing.jack_structure.class_methods.method_parameter.ParameterList;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class SubRoutineBody extends JackCommand 
{
    private SymbolToken LeftCurlyBracket;
    private VarDec varDec;
    private Statements statement;
    private SymbolToken RightCurlyBracket;
    static public ParameterList parameters;

    public SubRoutineBody(ParameterList parameters)
    {
        this.parameters = parameters;
        
        this.getLeftCurlyBracket();
        this.varDec = new VarDec();
        this.statement = new Statements(varDec);
        this.getRightCurlyBracket();
    }

    public String generateXMLCode()
    {
        String xmlCode = "<subroutineBody>\n";
        xmlCode += this.LeftCurlyBracket.generateXMLCode() + "\n";
        xmlCode += this.varDec.generateXMLCode() + "\n";
        xmlCode += this.statement.generateXMLCode() + "\n";
        xmlCode += this.RightCurlyBracket.generateXMLCode() + "\n";
        xmlCode += "</subroutineBody>";
        return xmlCode;
    }

    public void getLeftCurlyBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("{"))
        {
            SymbolToken leftCurlyBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.LeftCurlyBracket = leftCurlyBracket;
            System.out.println("Success: Check left curly bracket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                                        CompilationEngine.expectedRatharThanErrorMessage("'{' symbol token", token.getBody()), 
                                        token.getPosition()));
            System.out.println("Faile: Check left curly bracket.");
        }
    }

    public void getRightCurlyBracket()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("}"))
        {
            SymbolToken rightCurlyBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.RightCurlyBracket = rightCurlyBracket;
            System.out.println("Success: Check right curly bracket.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                                        CompilationEngine.expectedRatharThanErrorMessage("'{' symbol token", token.getBody()), 
                                        token.getPosition()));
            System.out.println("Fail: Check right curly bracket.");
        }
    }
    
    public String generateVMCode()
    {
        this.varDec.generateVMCode();
        String vmCode = this.statement.generateVMCode();

        return vmCode;
    }
}
