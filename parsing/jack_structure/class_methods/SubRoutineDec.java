package parsing.jack_structure.class_methods;

import java.util.ArrayList;

import parsing.JackCommand;

public class SubRoutineDec extends JackCommand
{
    public ArrayList<SubRoutineDec_C> subRutines = new ArrayList<>();

    public SubRoutineDec()
    {
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
    
}
