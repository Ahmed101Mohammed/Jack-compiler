package parsing.jack_structure.class_vars;

import java.util.ArrayList;

import parsing.CompilationEngine;
import parsing.JackCommand;

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

    public ArrayList<ClassVarDec_C> getVars() {
        return vars;
    }
}
