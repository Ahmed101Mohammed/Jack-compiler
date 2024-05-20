import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.Token;

class JackAnalyzer
{
	public static String relativeProjectRoot;
    // ./test-workspace/Main.jack
    public static void main(String[] args) {
        ArrayList<File> neededCompileFiles = getNeededToCompileFiles();
        printNamesOfListOfFiles(neededCompileFiles);
        ArrayList<JackTokenizer> filesTokenized = tokenizeListOfJackFiles(neededCompileFiles);
        if(Token.allTokensErrors.size() != 0)
        {
            printErrors();
        }
        ArrayList<JackCommand> commands = parseListOfTokenizedFiles(filesTokenized);
    }

    public static ArrayList<JackCommand> parseListOfTokenizedFiles(ArrayList<JackTokenizer> tokenizedFiles)
    {
        if(tokenizedFiles.size() == 0)
        {
            System.out.println("There are no tokenized files to parse.");
            return null;
        }
        ArrayList<JackCommand> commands = new ArrayList<>();
        System.out.println("---Parsing Started---");
        for(JackTokenizer tokenizedFile:tokenizedFiles)
        {
            System.out.println("- Parse: " + tokenizedFile.file.getName() + " file.");
            CompilationEngine CE = new CompilationEngine(tokenizedFile.getTokens(), 
                                        relativeProjectRoot, 
                                        tokenizedFile.getFile());
            commands.add(CE.getProgram());
        }
        System.out.println("---Parsing End---");
        return commands;
    }

    public static ArrayList<JackTokenizer> tokenizeListOfJackFiles(ArrayList<File> jackFiles)
    {
        if(jackFiles.size() == 0)
        {
            System.out.println("There are no jack files to tokenize.");
            return null;
        }
        ArrayList<JackTokenizer> tokenizedFiles = new ArrayList<>();
        System.out.println("---Tokenizing Started---");
        for(File file:jackFiles)
        {
            JackTokenizer newJackTokenizer = new JackTokenizer(file);
            tokenizedFiles.add(newJackTokenizer);
            System.out.println("---Tokenizing of " + file.getName() + " is Done---");
        }
        System.out.println("---Tokenizing finished---");

        return tokenizedFiles;
    }

    public static ArrayList<File> getNeededToCompileFiles()
    {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter your file/folder path [Note: The path should start from the root of the project.]: ");
        String path = userInput.nextLine();
        userInput.close();
        ArrayList<File> jackFiles = getJackFiles(path);
		relativeProjectRoot = getRootPath(path);
        return jackFiles;
    }

	private static String getRootPath(String path)
	{
		File rootFileOrFolder = new File(path);
		String rootPath = "";
		if(rootFileOrFolder.isDirectory())
		{
			char[] pathChars = path.toCharArray();
			char lastPathChar = pathChars[pathChars.length-1];
			if(lastPathChar != '/'){path += "/";}
			rootPath = path;
		}
		else
		{
			String[] parsingPath = path.split("/");
			for(int i = 0; i < parsingPath.length-1; i++)
			{
				rootPath += parsingPath[i]  + "/";
			}
		}
		return rootPath;
	}

    public static ArrayList<File> getJackFiles(String path)
    {
        File mainFile = new File(path);
        ArrayList<File> jackFiles = new ArrayList<>();
        if(mainFile.isDirectory())
        {
            for (File subFile:mainFile.listFiles())
            {
               if (isFileExtensionIs(subFile, "jack")) 
               {
                    jackFiles.add(subFile);
               }
            }
        }
        else
        {
            if(isFileExtensionIs(mainFile, "jack"))
            {
                jackFiles.add(mainFile);
            }
        }
        return jackFiles;
    }

    public static boolean isFileExtensionIs(File file, String extension)
    {
        String fileName = file.getName();
        String[] fileNameParts = fileName.split("\\.");
        String actualExtension = fileNameParts[fileNameParts.length-1];
        if(!file.isDirectory() && (actualExtension.equals(extension)))
        {
            return true;
        }
        return false;
    }

    public static void printNamesOfListOfFiles(ArrayList<File> files)
    {

        System.out.println("---Files---");
        for(File file:files)
        {
            System.out.println(file.getName());
        }
        System.out.println("-----------");
    }

    public static void printErrors()
    {
        for(helperClasses.Error error:Token.allTokensErrors)
        {
            error.print();
        }
    }

    public static void printTokenizersResults(ArrayList<JackTokenizer> tokenizers)
    {
        for(JackTokenizer tokenizer: tokenizers)
        {
            tokenizer.print();
        }
    }
}
