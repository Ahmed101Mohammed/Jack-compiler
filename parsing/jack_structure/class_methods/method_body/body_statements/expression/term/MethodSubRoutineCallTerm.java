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

public class MethodSubRoutineCallTerm implements IsubRoutineCallTerm
{
    private IdentifierToken caller;
    private SymbolToken dot;
    private IdentifierToken subRoutineName;
    private SymbolToken leftParanthes;
    private ArrayList<Expression> expressionList = new ArrayList<>();
    private SymbolToken rightParanthes;

    public MethodSubRoutineCallTerm()
    {
        this.getCaller();
        this.getDot();
        this.getSubRoutineName();
        this.getLeftParanthes();
        this.getAllExpressions();
        this.getRightParanthes();
    }
    // OO Jack
    private void getSubRoutineName() // Complate from here.
    {
        // [ ] I thing we forget to determine of variables is defined in teh prameter list.
        // [ ] Find way to get the class name of predefined variable.
        // [ ] Find way to store the subroutiense the say they is part of some class. {subRoutineName: className}
        // [ ] 
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

        if(token.getType() == TokenType.Identifier && 
        (Statements.subRoutineVars.isVarDublicatedInSubRoutineBody(token) ||
        SubRoutineDec.classVars.isNameDublicateeInClassScope(token.getBody()) || 
        Class_C.isClassDefinedBefore(token.getBody())))
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
        // TODO Auto-generated method stub
        return null;
    }    
}
