package vmWrtier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import parsing.JackCommand;

public class VMWriter {
    private String relativeFilePath;
    private JackCommand programm;
    public VMWriter(String relativePath, JackCommand programm)
    {
        this.relativeFilePath = relativePath;
        this.programm = programm;
        generateVMFile();
    }

    public void generateVMCode(File vmFile)
	{
		// Generate vm file.
        try
        {
            FileWriter xmlFileWriter = new FileWriter(vmFile);
            xmlFileWriter.write(this.programm.generateVMCode());
            xmlFileWriter.close();
        } 
        catch(IOException e)
        {
            System.out.println("Failed to create xml Write file, because of: " + e.getMessage());
        }
	}

	public void generateVMFile()
    {
        try
        {
            File vm = new File(this.getFileNewName());
            System.out.println("The name of vm file is: " + vm.getName());
			System.out.println("The vm file normal path is: " + vm.getPath());
            System.out.println("The vm file absolute path is: " + vm.getAbsolutePath());
            vm.createNewFile();
            this.generateVMCode(vm);
        }
        catch(IOException e)
        {
            System.out.println("Filed to create xml file for " + this.programm.getName() + " file. Because of: " + e.getMessage());
        }
    }

    private String getFileNewName()
    {
        String newFileName =  this.relativeFilePath + this.programm.getName() + ".vm";
        return newFileName;
    }
}
