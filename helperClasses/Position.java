package helperClasses;

public class Position {
    private int firstLine;
    private int lastLine;
    private int firstChar;
    private int lastChar;

    public Position(int firstLine, int lastLine, int firstChar, int lastChar)
    {
        this.firstLine = firstLine;
        this.lastLine = lastLine;
        this.firstChar = firstChar;
        this.lastChar = lastChar;
    }

    public int getFirstChar() {
        return firstChar;
    }

    public int getFirstLine() {
        return firstLine;
    }

    public int getLastChar() {
        return lastChar;
    }

    public int getLastLine() {
        return lastLine;
    }

    public String getPositionInString()
    {
        String postionString = "From " + this.firstLine + ":" + this.firstChar + " To " + this.lastLine + ":" + this.lastChar;
        return postionString;
    }
    
}
