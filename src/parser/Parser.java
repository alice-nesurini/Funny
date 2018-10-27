package parser;

import structure.Expr;
import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final Tokenizer tokenizer;
    private Token currentToken;
    private Token pastToken;

    public Parser(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
        this.tokenizer=tokenizer;
        next();
        program();
    }

    private Expr program() throws IOException, TokenizerException, ParserException {
        Expr expr=function();
        check(TokenType.EOS, "no valid end of stream found");
        return expr;
    }

    private Expr function() throws IOException, TokenizerException, ParserException {
        checkAndNext(TokenType.OPEN_CURLY_BRACKET, "open '{' error");
        List<String> params=optParams();
        List<String> locals=optLocals();

        params.addAll(locals);
        LookupTable lookupTable=new LookupTable(params, null);
        Expr expr=optSequence(lookupTable);
        checkAndNext(TokenType.CLOSE_CURLY_BRACKET, "close '}' error");
        return expr;
    }

    private Expr optSequence(LookupTable lookupTable) throws ParserException, IOException, TokenizerException {
        checkAndNext(TokenType.ARROW, "starting ARROW (->) was expected");
        lookupTable.viewLookupTable();
        return new Expr();
    }

    private List<String> optLocals() throws TokenizerException, ParserException, IOException {
        return optIds();
    }

    private List<String> optParams() throws TokenizerException, ParserException, IOException {
        if(currentToken.getType()==TokenType.OPEN_ROUND_BRACKET) {
            next();
            List<String> ids = optIds();
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "close ')' error");
            return ids;
        }
        //no optional
        return new ArrayList<>();
    }

    private List<String> optIds() throws ParserException, IOException, TokenizerException {
        //Scope - highest level
        //separated by space
        List<String> ids=new ArrayList<>();
        while(currentToken.getType()!=TokenType.ARROW) {
            ids.add(id());
            next();
        }
        return ids;
    }

    private String id() throws TokenizerException, ParserException, IOException {
        check(TokenType.ID, "identifer expected");
        return currentToken.getStringValue();
    }

    private void next() throws IOException, TokenizerException {
        pastToken=currentToken;
        currentToken=tokenizer.next();
    }

    private void check(TokenType type, String msg) throws ParserException {
        if(currentToken.getType()!=type)
            throw new ParserException("[parsing] "+msg);
    }
    private void checkAndNext(TokenType type, String msg) throws ParserException, IOException, TokenizerException {
        if(currentToken.getType()!=type)
            throw new ParserException("[parsing] "+msg);
        next();
    }
}
