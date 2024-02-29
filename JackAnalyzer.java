import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class JackAnalyzer
{
    public static void main(String[] args) {
        ArrayList<File> neededCompileFiles = getNeededToCompileFiles();
        printNamesOfListOfFiles(neededCompileFiles);
    }

    public static ArrayList<File> getNeededToCompileFiles()
    {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter your file/folder path [Note: The path should start from the root of the project.]: ");
        String path = userInput.nextLine();
        ArrayList<File> jackFiles = getJackFiles(path);
        return jackFiles;
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
}