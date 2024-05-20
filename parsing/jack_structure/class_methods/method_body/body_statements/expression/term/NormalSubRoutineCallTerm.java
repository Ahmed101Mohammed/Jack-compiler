package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import java.lang.foreign.SymbolLookup;
import java.util.ArrayList;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.SubRoutineDec_C;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import parsing.jack_structure.class_methods.method_body.body_statements.whileStatement;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class NormalSubRoutineCallTerm implements IsubRoutineCallTerm
{
    private IdentifierToken subRoutineName;
    private SymbolToken leftParanthesSymbol;
    private ArrayList<Expression> expressionList = new ArrayList<>();
    private SymbolToken rightParanthesSymbol;
    private ArrayList<Token> commas = new ArrayList<>();

    public NormalSubRoutineCallTerm()
    {
        this.getSubRoutineName();
        this.getLeftParanthes();
        this.getAllExpressions();
        this.getRightParanthes();
    }

    @Override
    public String generateXMLCode() {
        int expressionPointer = 0;
        int commaPointer = 0;
        String xmlCode = this.subRoutineName.generateXMLCode() + "\n";
        xmlCode += this.leftParanthesSymbol.generateXMLCode() + "\n";
        xmlCode += "<expressionList>\n";
        while(expressionPointer < this.expressionList.size() - 1)
        {
            xmlCode += this.expressionList.get(expressionPointer).generateXMLCode() + "\n";
            xmlCode += this.commas.get(commaPointer).generateXMLCode() + "\n";
            expressionPointer += 1;
            commaPointer += 1;
        }

        if(expressionPointer < this.expressionList.size())
        {
            xmlCode += this.expressionList.get(expressionPointer).generateXMLCode() + "\n";
        }

        xmlCode += "</expressionList>\n";
        xmlCode += this.rightParanthesSymbol.generateXMLCode();
        return xmlCode;
    }

    private void getAllExpressions()
    {
        if(!isThereExpression()){return;}

        do
        {
            Expression newExpression = new Expression();
            this.expressionList.add(newExpression);
        }while(isThereMoreExpression());
    }

    private boolean isThereMoreExpression()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals(","))
        {
            this.commas.add(token);
            return true;
        }
        CompilationEngine.decrementCurrentIndexByOne();
        return false;
    }

    private boolean isThereExpression()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            return false;
        }
        return true;
    }
    private void getRightParanthes()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            SymbolToken rightParanthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightParanthesSymbol = rightParanthes;
            System.out.println("Success: Check right paranthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("'(' SymbolToken", token.getBody())));
            System.out.println("Faile: Check left paranthes.");
        }
    }

    private void getLeftParanthes()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("("))
        {
            SymbolToken leftParanthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftParanthesSymbol = leftParanthes;
            System.out.println("Success: Check left paranthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("'(' SymbolToken", token.getBody())));
            System.out.println("Faile: Check left paranthes.");
        }
    }
    private void getSubRoutineName()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Identifier)
        {
            IdentifierToken subRoutineName_ = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.subRoutineName = subRoutineName_;
            System.out.println("Success: Check subRoutine name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InValidSubRoutineName", "There no predefined subroutine with this name: " + token.getBody(), token.getPosition()));
            System.out.println("Faile: Check subRoutine name.");
        }
    }

    @Override
    public String generateVMCode() {
        // TODO Auto-generated method stub
        return null;
    }    
}
