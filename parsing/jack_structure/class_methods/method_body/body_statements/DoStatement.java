package parsing.jack_structure.class_methods.method_body.body_statements;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.term.SubRoutineCallTerm;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class DoStatement implements IStatement
{
    // 'do' subroutineCall';'
    private KeywordToken dokeyword;
    private SubRoutineCallTerm subRoutineCall;
    private SymbolToken simicolon;

    public DoStatement()
    {
        this.getDoKeyword();
        this.subRoutineCall = new SubRoutineCallTerm();
        this.getSimiColon();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = "<doStatement>\n";
        xmlCode += this.dokeyword.generateXMLCode() + "\n";
        xmlCode += this.subRoutineCall.generateXMLCode() + "\n";
        xmlCode += this.simicolon.generateXMLCode() + "\n";
        xmlCode += "</doStatement>";
        return xmlCode;
    }

    public void getDoKeyword()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("do"))
        {
            KeywordToken do_ = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.dokeyword = do_;
            System.out.println("Success: Check do keyword.");
        }
        else
        {
            System.out.println("Fail: Check do keyword.");
        }
    }

    public void getSimiColon()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
        {
            SymbolToken simicolon_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.simicolon = simicolon_;
            System.out.println("Success: Check simicolon.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("';' SymbolToken", token.getBody()), 
                token.getPosition()));
            System.out.println("Fail: Check simicolon.");
        }
    }
    @Override
    public String generateVMCode() {
        String vmCode = this.subRoutineCall.generateVMCode();
        vmCode += "pop temp 0\n";
        return vmCode;
    }    
}
