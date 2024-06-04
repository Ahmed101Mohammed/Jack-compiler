package parsing.jack_structure.class_methods.method_body.body_statements.expression.term;

import java.util.ArrayList;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.jack_structure.Class_C;
import parsing.jack_structure.class_methods.SubRoutineDec;
import parsing.jack_structure.class_methods.method_body.body_statements.Statements;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.Expression;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.Symbole;
import vmWrtier.symboleTable.SymboleTable;

public class MethodSubRoutineCallTerm implements IsubRoutineCallTerm
{
    private IdentifierToken caller;
    private SymbolToken dot;
    private IdentifierToken subRoutineName;
    private SymbolToken leftParanthes;
    private ArrayList<Expression> expressionList = new ArrayList<>();
    private SymbolToken rightParanthes;
    private ArrayList<Token> commas = new ArrayList<>();

    public MethodSubRoutineCallTerm()
    {
        this.getCaller();
        this.getDot();
        this.getSubRoutineName();
        this.getLeftParanthes();
        this.getAllExpressions();
        this.getRightParanthes();
    }

    @Override
    public String generateXMLCode() {
        int expressionPointer = 0;
        int commaPointer = 0;
        String xmlCode = this.caller.generateXMLCode() + "\n";
        xmlCode += this.dot.generateXMLCode() + "\n";
        xmlCode += this.subRoutineName.generateXMLCode() + "\n";
        xmlCode += this.leftParanthes.generateXMLCode() + "\n";
        xmlCode += "<expressionList>\n";
        while(expressionPointer < this.expressionList.size() -1)
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
        xmlCode += this.rightParanthes.generateXMLCode();
        return xmlCode;
    }
    // OO Jack
    private void getSubRoutineName() // Complate from here.
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier)
        {
            IdentifierToken method = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.subRoutineName = method;
            System.out.println("Success: Check subroutine name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
                CompilationEngine.expectedRatharThanErrorMessage("[variable name] IdentifierToken", token.getBody()),
                token.getPosition()));

            System.out.println("Faile: Check subrouine name.");
        }
    } 
    private void getDot()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Symbol && token.getBody().equals("."))
        {
            SymbolToken dot_ = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.dot = dot_;
            System.out.println("Success: Check dot.");
        }
    }

    private void getCaller()
    {
        Token token = CompilationEngine.advance();

        if(token.getType() == TokenType.Identifier)
        {
            IdentifierToken callerName = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.caller = callerName;
            System.out.println("Success: Check the caller.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InvalidCaller", 
                "There is no class name or predefined variable with this name: " + token.getBody(), 
                token.getPosition()));
            System.out.println("Faile: Check the caller.");
        }
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
            this.rightParanthes = rightParanthes;
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
            this.leftParanthes = leftParanthes;
            System.out.println("Success: Check left paranthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("'(' SymbolToken", token.getBody())));
            System.out.println("Faile: Check left paranthes.");
        }
    }
    @Override
    public String generateVMCode() {
        String vmCode = "";
        String methodClassName = this.caller.getBody();
        int argumentsNumber = this.expressionList.size();
        Symbole var = SymboleTable.getVar(this.caller.getBody());
        if(var != null)
        {
            vmCode += "push " + var.getSegment() + " " + var.getOrder() + "\n";
            methodClassName = var.getType();
            argumentsNumber += 1;
        }

        for(Expression exp:this.expressionList)
        {
            vmCode += exp.generateVMCode();
        }

        vmCode += "call " + methodClassName + "." + this.subRoutineName.getBody() + " " + argumentsNumber + "\n";
        return vmCode;
    }    
}
