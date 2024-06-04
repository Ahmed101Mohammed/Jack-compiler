package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.Symbole;
import vmWrtier.symboleTable.SymboleTable;

public class ArrayIndexingTerm implements Iterm
{
    private IdentifierToken varName;
    private SymbolToken leftSquareBracketSymbol;
    private Expression expression;
    private SymbolToken rightSquareBracketSymbol;

    public ArrayIndexingTerm()
    {
        this.getVarName();
        this.getLeftSquareBracket();
        this.expression = new Expression();
        this.getRightSquareBracket();
    }

    @Override
    public String generateXMLCode() {
        String xmlCode = this.varName.generateXMLCode() + "\n";
        xmlCode += this.leftSquareBracketSymbol.generateXMLCode() + "\n";
        xmlCode += this.expression.generateXMLCode() + "\n";
        xmlCode += this.rightSquareBracketSymbol.generateXMLCode();
        return xmlCode;
    }

    private void getRightSquareBracket()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("]"))
        {
            SymbolToken rightSquareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightSquareBracketSymbol = rightSquareBracket;
            System.out.println("Success: Check right square bracket."); 
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("']' SymbolToken", token.getBody()), token.getPosition()));
            System.out.println("Faile: Check right square braket.");
        }
    }

    private void getLeftSquareBracket()
    {
        Token token = CompilationEngine.advance();
        
        if(token.getType() == TokenType.Symbol && token.getBody().equals("["))
        {
            SymbolToken leftSquareBracket = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftSquareBracketSymbol = leftSquareBracket;
            System.out.println("Success: Check left square bracket.");
        }
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Identifier && 
        (Statements.subRoutineVars.isVarDublicatedInSubRoutineBody(token) || 
        SubRoutineDec.classVars.isNameDublicateeInClassScope(token.getBody())))
        {
            IdentifierToken var = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.varName = var;
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InvalidVariable", 
                "To use variable in Jack you should declared it.", token.getPosition()));
            System.out.println("Faile: Check variable name.");
        }
    }

    @Override
    public String generateVMCode() {
        String vmCode = "";
        Symbole var = SymboleTable.getVar(this.varName.getBody());
        vmCode += "push " + var.getSegment() + " " + var.getOrder() + "\n";
        vmCode += this.expression.generateVMCode();
        vmCode += "add\n";
        vmCode += "pop pointer 1\n";
        vmCode += "push that 0\n";
        return vmCode;
    }    
}
