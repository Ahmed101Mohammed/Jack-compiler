package parsing.jack_structure.class_methods.method_body.body_vars;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;
import tokens.TokenType;

public class VarDec extends JackCommand
{
    private ArrayList<VarDec_C> vars = new ArrayList<>();
    
    public VarDec()
    {
        while (this.thereIsVarKeyWord()) 
        {
            VarDec_C newOne = new VarDec_C(this);
            vars.add(newOne); 
            if(newOne.isNeededReview())
            {
                CompilationEngine.neededReviewVarDec.add(newOne);
            }    
        }
    }

    public String generateXMLCode()
    {
        String xmlCode = "";
        for(VarDec_C var: this.vars)
        {
            if(xmlCode.length() > 0)
            {
                xmlCode += "\n";
            }
            xmlCode += "<varDec>\n";
            xmlCode += var.generateXMLCode() + "\n";
            xmlCode += "</varDec>";
        }

        return xmlCode;
    }

    public boolean isVarDublicatedInSubRoutineBody(Token token)
    {
        for(VarDec_C varDec:this.getVars())
        {
            if(varDec.isVarDublicatedInVarStatement(token))
            {
                return true;
            }
        }
        return false;
    }

    private boolean thereIsVarKeyWord()
    {
        Token token = CompilationEngine.advance();
        CompilationEngine.decrementCurrentIndexByOne();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("var"))
        {
            return true;
        }
        return false;
    }

    public ArrayList<VarDec_C> getVars() {
        return vars;
    }

    @Override
    public String generateVMCode() 
    {
        for(VarDec_C var:this.vars)
        {
            var.generateVMCode();
        }    
        return null;
    }
}
