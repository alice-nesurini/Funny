package tokenizer;

import java.math.BigDecimal;

public class Token{

    private final TokenType type;
    //only for number
    private BigDecimal value;
    //only var name or string
    private String stringValue;

    public Token(TokenType type){
        this.type=type;
    }

    public Token(TokenType type, BigDecimal value){
        this.type=type;
        this.value=value;
    }

    public Token(TokenType type, String id){
        this.type=type;
        this.stringValue =id;
    }

    public TokenType getType(){
        return type;
    }

    public BigDecimal getValue(){
        return value;
    }

    public String getStringValue(){
        return stringValue;
    }
}