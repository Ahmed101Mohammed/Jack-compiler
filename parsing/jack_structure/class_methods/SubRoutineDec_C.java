package parsing.jack_structure.class_methods;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_body.SubRoutineBody;
import parsing.jack_structure.class_methods.method_parameter.ParameterList;
import tokens.IdentifierToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.SymboleTable;

public class SubRoutineDec_C extends JackCommand
{
    private SubRoutineKind subRoutineKind = null;
    private Token subRoutineKindToken; // not part of the structure.
    private SubRoutineType type;   
    private IdentifierToken subRoutineName;
    private SymbolToken leftParenthes;
    private ParameterList parameterList;
    private SymbolToken rightParenthes;
    private SubRoutineBody subRoutineBody;
    // Other members
    static public SubRoutineDec subRoutineDecParent;
    private boolean isCompletedStructure = false;

    public SubRoutineDec_C(SubRoutineDec src)
    {
        this.subRoutineDecParent = src;
        this.getSubRoutineKind();   
        if(this.subRoutineKind != null)
        {
            this.type = new SubRoutineType();
            this.getSubRoutineName();
            this.getLeftParenthes();
            this.parameterList = new ParameterList();
            this.getRightParenthes();
            this.subRoutineBody = new SubRoutineBody(parameterList);
            this.isCompletedStructure = true;
        }
    }

    public String generateXMlCode()
    {
        String xmlCode = this.subRoutineKindToken.generateXMLCode() + "\n";
        xmlCode += this.type.generateXMLCode() + "\n";
        xmlCode += this.subRoutineName.generateXMLCode() + "\n";
        xmlCode += this.leftParenthes.generateXMLCode() + "\n";
        xmlCode += this.parameterList.generateXMLCode() + "\n";
        xmlCode += this.rightParenthes.generateXMLCode() + "\n";
        xmlCode += this.subRoutineBody.generateXMLCode();
        return xmlCode;
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
        if(token.getType() == TokenType.Identifier && !subRoutineDecParent.isDublicatedSubRoutineName(token))
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
    
    private void getSubRoutineKind()
    {
        Token token = CompilationEngine.advance();
        SubRoutineKind subRoutineKind = setSubRoutineKind(token.getBody());
        if(token.getType() == TokenType.Keyword && subRoutineKind != null)
        {
            this.subRoutineKind = subRoutineKind;
            this.subRoutineKindToken = token;
            System.out.println("Success: Check subroutine kind.");
        }
        else
        {
            CompilationEngine.decrementCurrentIndexByOne();
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

    public String generateVMCode()
    {
        SymboleTable.resetSubRoutineLevelSymboles();
        this.parameterList.generateVMCode(this.subRoutineKind);
        String bodyCode = this.subRoutineBody.generateVMCode();
        String vmCode = "function " + SymboleTable.getClassName() + "." + this.subRoutineName.getBody() + 
                        " " + SymboleTable.getLocalOrder() + "\n";
        vmCode += this.generateIntroCodeDependOnSubRoutineKind();
        vmCode += bodyCode;
        return vmCode;
    }

    public String generateIntroCodeDependOnSubRoutineKind()
    {
        String vmCode = "";
        if(this.subRoutineKind == SubRoutineKind.Constructor)
        {
            vmCode += "push constant" + SymboleTable.getFieldOrder() + "\n"
                    + "call Memory.alloc 1\n" + "pop pointer 0\n";
        }
        else if(this.subRoutineKind == SubRoutineKind.Method)
        {

            vmCode += "push argument 0\n" + "pop pointer 0\n";
        }

        return vmCode;
    }
}
