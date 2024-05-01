package parsing.jack_structure.class_methods.method_body.body_vars;

import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat.Type;

import helperClasses.Error;
import parsing.CompilationEngine;
import parsing.JackCommand;
import tokens.IdentifierToken;
import tokens.KeywordToken;
import tokens.SymbolToken;
import tokens.Token;
import tokens.TokenType;

public class VarDec_C extends JackCommand
{
    private KeywordToken varKeyWord;
    private parsing.jack_structure.Type type;
    private ArrayList<IdentifierToken> varsNames = new ArrayList<>();
    private SymbolToken semiColon;

    private VarDec parent;

    public VarDec_C(VarDec parent)
    {
        this.parent = parent;
    
        this.getVarKeyWord();
        this.type = new parsing.jack_structure.Type();
        this.getVarsNames();
    }

    private void getVarsNames()
    {
        this.getVarName();
    }

    private void getVarName()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier && !this.isVarDublicated())
        {
            IdentifierToken varName = IdentifierToken.createIdentifierToken(token.getBody(), token.getPosition());
            this.varsNames.add(varName);
            System.out.println("Success: Check variable name.");
        }
        else
        {
            Token.allTokensErrors.add(new Error("InvalidVariable", 
                                        token.getBody() + "is not a valid variable in jack, your variable should be not dublicated in name and be identifier token.",
                                        token.getPosition());
            System.out.println("Faile: Check variable name.");
        }
    }

    private boolean isVarDublicated()
    {
        
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


    
}
