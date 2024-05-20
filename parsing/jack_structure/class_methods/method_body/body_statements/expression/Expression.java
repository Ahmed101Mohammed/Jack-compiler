package parsing.jack_structure.class_methods.method_body.body_statements.expression;

import java.util.ArrayList;

import parsing.jack_structure.class_methods.method_body.body_statements.expression.op.Op;
import parsing.jack_structure.class_methods.method_body.body_statements.expression.term.Term;

public class Expression 
{
    // term
    private ArrayList<Term> terms = new ArrayList<>();
    // op
    private ArrayList<Op> ops = new ArrayList<>();

    // expresoin
    private Expression expression;

    // constructor:
    public Expression()
    {
        Term term = new Term();
        terms.add(term);
        
        Op op = new Op();

        while(op.isValidOp())
        {
            ops.add(op);
            term = new Term();
            terms.add(term);
            op = new Op();
        }
    }

    public String generateXMLCode()
    {
        int termPointer = 0;
        int opPointer = 0;
        String xmlCode = "<expression>\n";
        while(termPointer < terms.size() - 1)
        {
            xmlCode += terms.get(termPointer).generateXMLCode() + "\n";
            xmlCode += ops.get(opPointer).generateXMLCode() + "\n";
            termPointer += 1;
            opPointer += 1;
        }

        xmlCode += terms.get(termPointer).generateXMLCode() + "\n";

        xmlCode += "</expression>";
        return xmlCode;
    }
}
