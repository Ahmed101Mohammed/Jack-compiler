package parsing;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import helperClasses.Error;
import parsing.jack_structure.Class_C;
import parsing.jack_structure.class_methods.method_body.body_vars.VarDec_C;
import parsing.jack_structure.class_methods.method_parameter.Parameter_C;
import parsing.jack_structure.class_vars.ClassVarDec_C;
import tokens.Token;
import tokens.TokenType;

public class CompilationEngine
{
	private String relativeProjectRoot;
	private File file;
	public static ArrayList<Token> programTokens = new ArrayList<>();
	public static ArrayList<ClassVarDec_C> neededReviewForIdentfierType = new ArrayList<>();
	public static ArrayList<Parameter_C> neededReviewParameter = new ArrayList<>();
	public static ArrayList<VarDec_C> neededReviewVarDec = new ArrayList<>();
	public static int currentIndex;
	private Class_C program;
	public CompilationEngine(ArrayList<Token> tokens, String relativeProjectRoot, File file)
	{
		this.relativeProjectRoot = relativeProjectRoot;
		this.file = file;
		currentIndex = -1;
		programTokens = tokens;		
		this.program =  new Class_C();
		this.generateXmlFile();
	}

	public static Token advance()
	{
		currentIndex += 1;
		if(currentIndex > programTokens.size()-1){return null;}
		return programTokens.get(currentIndex);
	}

	public static void setCurrentIndex(int index)
	{
		currentIndex = index;
	}

	public static void decrementCurrentIndexByOne()
	{
		currentIndex -= 1;
	}

	public static Token getSpecificToken(int index)
	{
		if(index < 0 || index > programTokens.size()-1)
		{
			return null;
		}
		return programTokens.get(index);
	}

	public Class_C getProgram() {
		return program;
	}

	public static int getCurrentIndex()
	{
		return currentIndex;
	}

	public static void checkAllVariablesTypesValidation()	
	{
		for(ClassVarDec_C var:neededReviewForIdentfierType )
		{
			if(!Class_C.isClassDefinedBefore(var.getType().getBody()))
			{
				Token.allTokensErrors.add(new Error("InValidVariableType", 
										"This type: "+ var.getType().getBody() +" deos not valid. Jack accept one of these types: 'int' 'char' 'boolean' [Predefined Class Name].",
										var.getType().getPosition()));
			}
		}
	}

	
	public void generateParsingResultInXMLFormate(File xmFile)
	{
		// Generate xml file.
        try
        {
            FileWriter xmlFileWriter = new FileWriter(xmFile);
            xmlFileWriter.write(clearStringFromReapetedNewLines(this.program.generateXMLCode()));
            xmlFileWriter.close();
        } 
        catch(IOException e)
        {
            System.out.println("Failed to create xml Write file, because of: " + e.getMessage());
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
            this.generateParsingResultInXMLFormate(xml);
        }
        catch(IOException e)
        {
            System.out.println("Filed to create xml file for " + this.file.getName() + " file. Because of: " + e.getMessage());
        }
    }

    private String getFileNewName()
    {
        String newFileName =  this.relativeProjectRoot + this.getFileNameWithoutExtension() + "PM" + ".xml";
        return newFileName;
    }

    private String getFileNameWithoutExtension()
    {
        String fileName = this.file.getName();
        String[] fileNameParts = fileName.split("\\.");
        String actualName = fileNameParts[0];
        return actualName;
    }

	public static String expectedRatharThanErrorMessage(String expectedToken, String existToken)
	{
		return String.format("Expected %s, rather than this token: %s", expectedToken, existToken);
	}

	public static String clearStringFromReapetedNewLines(String str)
    {
        boolean thereIsNewLine = false;
        String pureStr = "";
        for(char c:str.toCharArray())
        {
            if(c == '\n' && thereIsNewLine)
            {
                continue;
            }
            else if(c == '\n')
            {
                thereIsNewLine = true;
            }
            else
            {
                thereIsNewLine = false;
            }
            pureStr += c;
        }
        return pureStr;
    }

}
