package helperClasses;

public class Error {
    private String name;
    private String message;
    private Position tokenPosition = null;
    
    public Error(String name, String message, Position tokenPosition)
    {
        this.name = name;
        this.message = message;
        this.tokenPosition = tokenPosition;
    }

    public Error(String name, String message)
    {
        this.name = name;
        this.message = message;
    }

    public void print()
    {
        if(this.tokenPosition == null)
        {
            System.out.println(this.name + ": " + this.message);
        }
        System.out.println(this.name + ": " + this.message + " Error position is " + this.tokenPosition.getPositionInString());
    }
}
