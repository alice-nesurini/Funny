package parser;

import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class Parser {


    private final Tokenizer tokenizer;
    private Token currentToken;
    private Token pastToken;

    public Parser(Tokenizer tokenizer) throws IOException, TokenizerException {
        this.tokenizer=tokenizer;
        next();
        program();
    }

    private void program() {
        //function();
        if(currentToken.getType()== TokenType.EOS) return /*End*/;
    }

    private void next() throws IOException, TokenizerException {
        currentToken=tokenizer.next();
    }
}
