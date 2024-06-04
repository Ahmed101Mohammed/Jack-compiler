package parsing.jack_structure.class_methods;

import java.util.ArrayList;

import parsing.JackCommand;
import parsing.jack_structure.class_vars.ClassVarDec;
import tokens.Token;

public class SubRoutineDec extends JackCommand
{
    private ArrayList<SubRoutineDec_C> subRutines = new ArrayList<>();
    // class vars
    static public ClassVarDec classVars;

    public SubRoutineDec(ClassVarDec classVars)    
    {
        this.classVars = classVars;
        while (true) 
        {
            SubRoutineDec_C oneRoutine = new SubRoutineDec_C(this);
            if(!oneRoutine.isCompletedStructure())
            {
                break;
            } 
            this.subRutines.add(oneRoutine);    
        }
    }

    public String generateXMLCode()
    {
        String xmlCode = "";

        for(SubRoutineDec_C subRoutine: this.subRutines)
        {
            if(xmlCode.length() > 0)
            {
                xmlCode += "\n";
            }
            xmlCode += "<subroutineDec>\n";
            xmlCode += subRoutine.generateXMlCode() + "\n";
            xmlCode += "</subroutineDec>";
        }
        
        return xmlCode;
    }    
    
    public boolean isDublicatedSubRoutineName(Token token)
    {
        for(SubRoutineDec_C sub: subRutines)
        {
            if(sub.getPureName().equals(token.getBody()))
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<SubRoutineDec_C> getSubRutines() {
        return subRutines;
    }

    public String generateVMCode()
    {
        String vmCode = "";

        for(SubRoutineDec_C subRoutine:this.subRutines)
        {
            vmCode += subRoutine.generateVMCode();
        }

        return vmCode;
    }

}
