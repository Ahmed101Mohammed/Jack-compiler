package parsing.jack_structure.class_methods.method_body.body_vars;

import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat.Type;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import parsing.jack_structure.class_methods.method_body.SubRoutineBody;
import tokens.IdentifierToken;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;
import vmWrtier.symboleTable.SymboleTable;

public class VarDec_C extends JackCommand
{
    private KeywordToken varKeyWord;
    private parsing.jack_structure.Type type;
    private ArrayList<IdentifierToken> varsNames = new ArrayList<>();
    private ArrayList<Token> commas = new ArrayList<>();
    private SymbolToken semiColon;

    private VarDec parent;

    public VarDec_C(VarDec parent)
    {
        this.parent = parent;
    
        this.getVarKeyWord();
        this.type = new parsing.jack_structure.Type();
        this.getVarsNames();
        this.getSemiColon();
    }

    public String generateXMLCode()
    {
        int varNamePointer = 0;
        int commaPointer = 0;
        String xmlCode = this.varKeyWord.generateXMLCode() + "\n";
        xmlCode += this.type.generateXMlCode() + "\n";

        while(varNamePointer < this.varsNames.size() - 1)
        {
            xmlCode += this.varsNames.get(varNamePointer).generateXMLCode() + "\n";
            xmlCode += this.commas.get(commaPointer).generateXMLCode() + "\n";
            varNamePointer += 1;
            commaPointer += 1;
        }
        if(varNamePointer < this.varsNames.size())
        {
            xmlCode += this.varsNames.get(varNamePointer).generateXMLCode() + "\n";
        }
        
        xmlCode += this.semiColon.generateXMLCode();
        return xmlCode;
    }

    private void getSemiColon()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(";"))
        {
            SymbolToken semiColon = SymbolToken.createSymbolToken(token.getBody(), token.getPosition());
            this.semiColon = semiColon;
            System.out.println("Success: Check semicolon.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("UnExpectedToken", 
            CompilationEngine.expectedRatharThanErrorMessage("';' Symbol Token", token.getBody()), token.getPosition()));
            System.out.println("Failed: Check semicolon.");
        }
    }
    private void getVarsNames()
    {  
        do
        {
            this.getVarName();
        }while(this.isThereAVariable());
    }

    private boolean isThereAVariable()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Symbol && token.getBody().equals(","))
        {
            this.commas.add(token);
            return true;
        }
        CompilationEngine.decrementCurrentIndexByOne();
        return false;
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier && !this.isVarDublicated(token))
        {
            IdentifierToken varName = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.varsNames.add(varName);
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InvalidVariable", 
                                        token.getBody() + "is not a valid variable in jack, your variable should be not dublicated in name and be identifier token.",
                                        token.getPosition()));
            System.out.println("Faile: Check variable name.");
        }
    }

    private boolean isVarDublicated(Token token)
    {
        IdentifierToken var = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
        if(this.isVarDublicatedInVarStatement(token) || 
            this.parent.isVarDublicatedInSubRoutineBody(token) || 
            SubRoutineBody.parameters.isIdentifierDublicated(var))
        {
            return true;
        }
        return false;
    }

    public boolean isVarDublicatedInVarStatement(Token token)
    {
        String varName = token.getBody();
        for(IdentifierToken var:varsNames)
        {
            if(var.getBody().equals(varName)){return true;}
        }
        return false;        
    }

    

    public void getVarKeyWord()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Keyword && token.getBody().equals("var"))
        {
            KeywordToken var = KeywordToken.createKeywordToken(token.getBody(), token.getPosition());
            this.varKeyWord = var;
            System.out.println("Success: Check var keyword.");
        }
    }

    public boolean isNeededReview()
    {
        return (this.type.getTokenType() == TokenType.Identifier);
    }

    public parsing.jack_structure.Type getType()
    {
        return this.type;
    }

    public String generateVMCode()
    {
        String typeName = this.type.getBody();
        for(IdentifierToken var:this.varsNames)
        {
            SymboleTable.addVAr(var.getBody(), typeName, "local");
        }
        return null;
    }
    
}
