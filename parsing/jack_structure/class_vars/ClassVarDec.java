package parsing.jack_structure.class_vars;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.IdentifierToken;

public class ClassVarDec extends JackCommand{
    private ArrayList<ClassVarDec_C> vars = new ArrayList<>();

    public ClassVarDec()
    {
        while (true)
        {
            ClassVarDec_C oneDec = new ClassVarDec_C(this);
            if(!oneDec.getComplateStructured())     
            {
                break;   
            }
            else
            {
                vars.add(oneDec);
                if(oneDec.isVarDecNeedReviewForType())
                {
                    CompilationEngine.neededReviewForIdentfierType.add(oneDec);
                }
            }
        }
    }

    public String generateXMLCode()
    {
        String xmlCode = "";
        for(ClassVarDec_C var: this.vars)
        {
            if(xmlCode.length() > 0)
            {
                xmlCode += "\n";
            }
            xmlCode += "<classVarDec>\n";
            xmlCode += var.generateXMLCode() + "\n";
            xmlCode += "</classVarDec>";
        }
        
        return xmlCode;
    }

    public boolean isNameDublicateeInClassScope(String varName)
    {
        for(ClassVarDec_C var:this.getVars())
        {
            for(IdentifierToken varNamePreDefine:var.getVariablesNames())
            {
                if(varNamePreDefine.getBody().equals(varName))
                {
                    return true;
                }
            }
        }

        return false;
    }
    public ArrayList<ClassVarDec_C> getVars() {
        return vars;
    }

    public String generateVMCode()
    {
        for(ClassVarDec_C var: this.vars)
        {
            var.generateVMCode();
        }
        return "";
    }
}
