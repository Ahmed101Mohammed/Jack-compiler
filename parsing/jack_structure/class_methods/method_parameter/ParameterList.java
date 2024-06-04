package parsing.jack_structure.class_methods.method_parameter;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.jack_structure.class_methods.SubRoutineKind;
import tokens.IdentifierToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.SymboleTable;

public class ParameterList {
    public ArrayList<Parameter_C> parameters = new ArrayList<>();
    public ArrayList<Token> commas = new ArrayList<>();

    public ParameterList()
    {
        if(!this.isThereParameters())
        {
            return;
        }

        do
        {
            Parameter_C oneParameter = new Parameter_C(this);
            this.parameters.add(oneParameter);
            if(oneParameter.isNeededToReviewType())
            {
                CompilationEngine.neededReviewParameter.add(oneParameter);
            }
            
        }while(this.isThereComma());
    }

    public String generateXMLCode()
    {
        int parameterPointer = 0;
        int commaPointer = 0;
        String xmlCode = "<parameterList>\n";
        while(parameterPointer < this.parameters.size()-1)
        {
            xmlCode += this.parameters.get(parameterPointer).generateXMLCode() + "\n";
            xmlCode += this.commas.get(commaPointer).generateXMLCode() + "\n";
            parameterPointer += 1;
            commaPointer += 1;
        }
        if(parameterPointer < this.parameters.size())
        {
            xmlCode += this.parameters.get(parameterPointer).generateXMLCode() + "\n";
        }

        xmlCode += "</parameterList>";
        return xmlCode;
    }

    public boolean isIdentifierDublicated(IdentifierToken token)
    {
        for(Parameter_C par:parameters)
        {
            if(par.getPureName().equals(token.getBody()))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isThereComma()
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

    private boolean isThereParameters()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(")"))
        {
            return false;
        }
        return true;
    }

    public String generateVMCode(SubRoutineKind kind)
    {
        if(kind == SubRoutineKind.Method)
        {
            SymboleTable.addVAr("this", SymboleTable.getClassName(), "argument");
        }

        for(Parameter_C parameter: this.parameters)
        {
            parameter.generateVMCode();
        }

        return "";
    }
}
