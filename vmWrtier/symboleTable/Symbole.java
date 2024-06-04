package vmWrtier.symboleTable;

public class Symbole {
    private String name;
    private String kind;
    private String type;
    private int order;

    public Symbole(String name, String type, String kind, int order)
    {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public int getOrder() {
        return order;
    }

    public String getSegment()
    {
        if(this.kind == "field")
        {
            return "this";
        }

        return this.kind;
    }
}
