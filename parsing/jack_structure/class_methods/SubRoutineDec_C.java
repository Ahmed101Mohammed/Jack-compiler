package parsing.jack_structure.class_methods;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_parameter.ParameterList;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class SubRoutineDec_C extends JackCommand
{
    private SubRoutineKind subRoutineKind = null;
    private SubRoutineType type;   
    private IdentifierToken subRoutineName;
    private SymbolToken leftParenthes;
    private ParameterList parameterList;
    private SymbolToken rightParenthes;
    // private SubRoutineBody subRoutineBody;
    // Other members
    private SubRoutineDec parent;
    private boolean isCompletedStructure = false;

    public SubRoutineDec_C(SubRoutineDec src)
    {
        this.parent = src;
        this.getSubRoutineKind();   
        if(this.subRoutineKind != null)
        {
            this.type = new SubRoutineType();
            this.getSubRoutineName();
            this.getLeftParenthes();
            this.parameterList = new ParameterList();
            this.getRightParenthes();
            // get subroutine body
        }
    }

    private void getLeftParenthes()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals("("))
        {
            SymbolToken leftParenthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.leftParenthes = leftParenthes;
            System.out.println("Success: Check left parenthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
                CompilationEngine.expectedRatharThanErrorMessage("'('", token.getBody()), 
                token.getPosition()));
            System.out.println("Faile: Check left parenthes.");
        }
    }

    private void getRightParenthes()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            SymbolToken rightParenthes = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.rightParenthes = rightParenthes;
            System.out.println("Success: Check right parenthes.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken",
                CompilationEngine.expectedRatharThanErrorMessage("')'", token.getBody()), 
                token.getPosition()));
            System.out.println("Faile: Check right parenthes.");
        }
    }

    private void getSubRoutineName()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier && !this.isDublicatedName(token))
        {
            IdentifierToken name = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.subRoutineName = name;
            System.out.println("Success: Check subroutine name.");
        }
        else
        {
            Token.allTokensErrors.add(new helperClasses.Error("InvalidSubRoutineName",
                                    "SubRoutine name in Jack should be Identifier and not dublicated in the same class, so this token '" + token.getBody() + "' is not valid.", 
                                    token.getPosition()));
            System.out.println("Faile: Check subroutine name.");
        }
    }

    private boolean isDublicatedName(Token token)
    {
        for(SubRoutineDec_C sub:this.parent.subRutines)
        {
            if(sub.getPureName().equals(token.getBody()))
            {
                return true;
            }
        }
        return false;
    }
    
    private void getSubRoutineKind()
    {
        Token token = CompilationEngine.advance();
        SubRoutineKind subRoutineKind = setSubRoutineKind(token.getBody());
        if(token.getType() == TokenType.Keyword && subRoutineKind != null)
        {
            this.subRoutineKind = subRoutineKind;
            System.out.println("Success: Check subroutine kind.");
        }
    }

    private SubRoutineKind setSubRoutineKind(String tokenBody)
    {
        SubRoutineKind subRoutineKind = null;
        switch (tokenBody) 
        {
            case "function":
                subRoutineKind = SubRoutineKind.Function;
                break;
            case "constructor":
                subRoutineKind = SubRoutineKind.Constructor;
                break;
            case "method":
                subRoutineKind = SubRoutineKind.Method;
                break;
            default:
                subRoutineKind = null;
                break;
        }
        return subRoutineKind;
    }

    public boolean isNeedForTypeReview()
    {
        if(this.type.getTokenType() == TokenType.Identifier)
        {
            return true;
        }
        return false;
    }

    public boolean isCompletedStructure() {
        return isCompletedStructure;
    }

    public String getPureName()
    {
        return this.subRoutineName.getBody();
    }
}
