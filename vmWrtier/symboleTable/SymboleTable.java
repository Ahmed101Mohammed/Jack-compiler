package vmWrtier.symboleTable;

import java.util.HashMap;

public class SymboleTable {
    private static HashMap<String, Symbole> classLevel = new HashMap<>();
    private static HashMap<String, Symbole> subroutineLevel = new HashMap<>();
    private static int staticOrder = -1;
    private static int fieldOrder = -1;
    private static int argumentOrder = -1;
    private static int localOrder = -1;

    public static void resetClassLevelSymboles()
    {
        classLevel.clear();
        staticOrder = -1;
        fieldOrder = -1;
        resetSubRoutineLevelSymboles();
    }

    public static void resetSubRoutineLevelSymboles()
    {
        subroutineLevel.clear();
        argumentOrder = -1;
        localOrder = -1;
    }

    public static void addVAr(String varName, String type, String kind)
    {
        int varOrder = getVarOrder(kind);
        Symbole var = new Symbole(varName, type, kind, varOrder);
        storeVar(var);
    }

    public static Symbole getVar(String varName)
    {
        Symbole var = subroutineLevel.get(varName);
        if(var == null)
        {
            var = classLevel.get(varName);
            if(var == null)
            {
                System.out.println("Fail to get the variable, may it does not declered.");
            }
        }
        
        return var;
    }

    private static void storeVar(Symbole var)
    {
        String kind = var.getKind();
        String varName = var.getName();

        if(kind.equals("static") || kind.equals("field"))
        {
            classLevel.put(varName, var);
        }
        else if(kind.equals("argument") || kind.equals("local"))
        {
            subroutineLevel.put(varName, var);
        }
        else
        {
            System.out.println("Fail to chose the var scope, bacause of there no kind type of variable called: '" + kind + "' in Jack.");
        }
    }

    private static int getVarOrder(String kind)
    {
        switch (kind) {
            case "static":
                staticOrder+=1;
                return staticOrder;
            case "field":
                fieldOrder += 1;
                return fieldOrder;
            case "argument":
                argumentOrder += 1;
                return argumentOrder;
            case "local":
                localOrder += 1;
                return localOrder;        
            default:
                System.out.println("Error: there is no variable kind called: '" + kind + "' in JACK.");
                break;
        }

        return -1;
    }

}
