package parsing.jack_structure.class_methods;

import helperClasses.Position;
import parsing.CompilationEngine;
import tokens.Token;
import tokens.TokenType;

public class SubRoutineType{
    private String body;
    private TokenType tokenType;
    private Position position;

    public SubRoutineType()
    {
        Token token = CompilationEngine.advance();
        if(token.getType() == TokenType.Identifier)
        {
            this.body = token.getBody();
            this.tokenType = token.getType();
            this.position = token.getPosition();
            this.successCheckingMessage();
        }
        else if(isKeyWordDataType(token))
        {
            this.body = token.getBody();
            this.tokenType = TokenType.Keyword;
            this.position = token.getPosition();
            this.successCheckingMessage();
        }
        else
        {
            Token.allTokensErrors.add(new helperClasses.Error("InValidType", 
                                "There is invalid type subRoutine, this token '"+ token.getBody() +"' is not a valid subRoutine type in Jack",
                                token.getPosition()));
            System.out.println("Failed: check subRoutine type.");
        }
    }

    public boolean isKeyWordDataType(Token token) {
        String tokenBody = token.getBody();
        if(token.getType() == TokenType.Keyword && 
            (tokenBody.equals("int") || tokenBody.equals("char") || tokenBody.equals("boolean") || tokenBody.equals("void")))
        {
            return true;
        }
        return false;
    }

    public void successCheckingMessage()
    {
        System.out.println("Success: Check subroutine type.");
    }

   public TokenType getTokenType() {
       return tokenType;
   } 
}
