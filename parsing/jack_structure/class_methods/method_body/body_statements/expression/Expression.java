package parsing.jack_structure.class_methods.method_body.body_statements.expression;

public class Expression 
{
    // term
    private Term term;
    // op
    private Op op = null;

    // expresoin
    private Expression expression;

    // constructor:
    public Expression()
    {
        // 1. check term. (F)
        this.term = new Term();

        // 2. check op. (C)? 3. check expresion : null
        this.op = new Op();

        if(this.op != null)
        {
            this.expression = new Expression();
        }
    }
}
