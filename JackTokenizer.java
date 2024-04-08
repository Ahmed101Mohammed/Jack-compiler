import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import helperClasses.Position;
import tokens.*;

public class JackTokenizer {
    protected File file;
    private BufferedReader fileReader;
    private ArrayList<Token> tokens = new ArrayList<>();
    private String line;

    public static void main(String[] args) {
        File newOne = new File("/test-workspace/Main.jack");
        System.out.println("Path: "+newOne.getAbsolutePath());
    }
    public JackTokenizer(File file)
    {
        this.file = file;
        try
        {
            this.fileReader = new BufferedReader(new FileReader(file));
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

        this.advance();
        this.generateXmlFile();
    }

    public void advance()
    {
        String token = "";
        boolean existOfNotClosedDoubleQauts = false;
        boolean existOfStartOfMuliLineCommand = false;
        int lastLinePosition = 1;
        int firstLinePosition = lastLinePosition;
        int firstCharPosition = 1;
        while (this.hasMoreTokents()) 
        {
            int lastCharPosition = 1;
            char[] lineChars = this.line.toCharArray();
            for(int index = 0; index < lineChars.length; index++)
            {
                if(token.length() == 0)
                {
                    firstCharPosition = lastCharPosition;
                    firstLinePosition = lastLinePosition;
                }

                char c = lineChars[index];
                if(!existOfNotClosedDoubleQauts && 
                    !existOfStartOfMuliLineCommand && 
                    SymbolToken.isSymbol(""+c) && 
                    !(""+c+this.getCharFromCharArrInStringFormat(index+1, lineChars)).equals("//") &&
                    !(""+c+this.getCharFromCharArrInStringFormat(index+1, lineChars)).equals("/*"))
                {
                    tokenType(token, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition-1), true);
                    tokenType(""+c, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition), true);
                    lastCharPosition++;
                    token = "";
                    continue;
                }
                else if(c == '"' && existOfNotClosedDoubleQauts == true)
                {
                    existOfNotClosedDoubleQauts = false;
                    tokenType(token+c, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition), true);
                    lastCharPosition++;
                    token = "";
                    continue;
                }
                else if(existOfNotClosedDoubleQauts){}
                else if(existOfStartOfMuliLineCommand && (""+this.getCharFromCharArrInStringFormat(index-1, lineChars)+c).equals("*/"))
                {
                    token = "";
                    existOfStartOfMuliLineCommand = false;
                    continue;
                }
                else if(existOfStartOfMuliLineCommand){}
                else if((""+c+this.getCharFromCharArrInStringFormat(index+1, lineChars)).equals("/*"))
                {
                    existOfStartOfMuliLineCommand = true;
                }
                else if((""+c+this.getCharFromCharArrInStringFormat(index+1, lineChars)).equals("//"))
                {
                    tokenType(token, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition-1), true);
                    token = "";
                    break;
                }
                else if(c == ' ' || c == '\t')
                {
                    tokenType(token, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition-1), true);
                    token = "";
                    lastCharPosition++;
                    continue;
                }
                else if(c == '"' && existOfNotClosedDoubleQauts == false)
                {
                    existOfNotClosedDoubleQauts = true;
                    tokenType(token, new Position(firstLinePosition, lastLinePosition, firstCharPosition, lastCharPosition-1), true);
                    token = "";
                    firstLinePosition = lastLinePosition;
                    firstCharPosition = lastCharPosition;
                }
                token += c;
                lastCharPosition++; 
            }
            lastLinePosition++; 
        }
    }

    public TokenType tokenType(String token, Position tokenPosition, boolean record)
    {
        TokenType tokenType = null;
        if(token.equals(""))
        {
            return tokenType;
        }
        else if(SymbolToken.isSymbol(token))
        {
            SymbolToken newSymbolToken = SymbolToken.createSymbolToken(token, tokenPosition);
            addToken(newSymbolToken, record);
            tokenType = TokenType.Symbol;
        }
        else if(KeywordToken.isKeywordToken(token))
        {
            KeywordToken newKeywordToken = KeywordToken.createKeywordToken(token, tokenPosition);
            addToken(newKeywordToken, record);
            tokenType = TokenType.Keyword;
        }
        else if(StringConstantToken.isStringConstantToken(token))
        {
            StringConstantToken newStringConstantToken = StringConstantToken.createStringConstantToken(token, tokenPosition);
            addToken(newStringConstantToken, record);
            tokenType = TokenType.StringConstant;
        }
        else if(IntegerConstantToken.isIntegerConstantToken(token, tokenPosition))
        {
            IntegerConstantToken newIntegerConstantToken = IntegerConstantToken.createIntegerConstantToken(token, tokenPosition);
            addToken(newIntegerConstantToken, record);
            tokenType = TokenType.IntegeralConstant;
        }
        else if(IdentifierToken.isIdentifierToken(token, tokenPosition))
        {
            IdentifierToken newIdentifierToken = IdentifierToken.createIdentifierToken(token, tokenPosition);
            addToken(newIdentifierToken, record);
            tokenType = TokenType.Identifier;
        }
        else
        {
            return null;
        }
        return tokenType;
    }

    public boolean hasMoreTokents()
    {
        try
        {
            this.line = this.fileReader.readLine();
            if(this.line != null)
            {
                return true;
            }
            return false;
        }
        catch(IOException e)
        {
            System.out.println("Field to know there are more tokens or not, because of: " + e.getMessage());
            return false;
        }
    }

    private void addToken(Token token, boolean record)
    {
        if(record)
        {
            this.tokens.add(token);
        }
    }

    private String getCharFromCharArrInStringFormat(int index, char[] chars)
    {
        if(index > chars.length-1 || index < 0)
        {
            return "";
        }
        return chars[index]+"";
    }

    public void print()
    {
        for(Token token:this.tokens)
        {
            System.out.println(token.generateXMLCode());
        }
    }

    public void generateXmlFile()
    {
        try
        {
            File xml = new File(this.getFileNewName());
            System.out.println("The name of xml file is: " + xml.getName());
			System.out.println("The xml file normal path is: " + xml.getPath());
            System.out.println("The xml file absolute path is: " + xml.getAbsolutePath());
            xml.createNewFile();
            this.writeContentInXML(xml);
        }
        catch(IOException e)
        {
            System.out.println("Filed to create xml tokens file for " + this.file.getName() + " file. Because of: " + e.getMessage());
        }
    }

    private String getFileNewName()
    {
        String fileFolderPath = this.file.getParentFile().getName();
        String newFileName = JackAnalyzer.relativeProjectRoot + this.getFileNameWithoutExtension() + "TM" + ".xml";
        return newFileName;
    }

    private String getFileNameWithoutExtension()
    {
        String fileName = this.file.getName();
        String[] fileNameParts = fileName.split("\\.");
        String actualName = fileNameParts[0];
        return actualName;
    }
    
    public void writeContentInXML(File xmFile)
    {
        String FullContent = "<tokens>\n";
        for(Token token: this.tokens)
        {
            FullContent += token.generateXMLCode() + "\n";
        }
		FullContent += "</tokens>";

        try
        {
            FileWriter xmlFileWriter = new FileWriter(xmFile);
            xmlFileWriter.write(FullContent);
            xmlFileWriter.close();
        } 
        catch(IOException e)
        {
            System.out.println("Failed to create xml Write file, because of: " + e.getMessage());
        }

    }
}
