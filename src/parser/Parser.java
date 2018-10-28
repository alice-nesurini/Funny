package parser;

import structure.BoolExpr;
import structure.Expr;
import structure.ExprSequence;
import structure.NumExpr;
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

    public Parser(Tokenizer tokenizer) {
        this.tokenizer=tokenizer;
    }

    public Expr parse() throws IOException, TokenizerException, ParserException {
        next();
        return program();
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
        return sequence(lookupTable);
    }

    private Expr sequence(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        //TODO: need a structure to keep list expr?
        List<Expr> exprs=new ArrayList<>();

        //case with one assignment (no semicolon end)
        Expr internalExpr=optAssignment(lookupTable);
        if(internalExpr!=null) {
            exprs.add(internalExpr);
        }

        //more assignments
        while(currentToken.getType()==TokenType.SEMICOLON){
            next();
            Expr expr=optAssignment(lookupTable);
            if(expr!=null) {
                exprs.add(expr);
            }
        }
        //TODO: just testing ev. structure extends Expr?
        return exprs.size()==0?null:new ExprSequence(exprs);
    }

    private Expr optAssignment(LookupTable lookupTable) throws IOException, ParserException, TokenizerException {
        return assignment(lookupTable);
    }

    private Expr assignment(LookupTable lookupTable) throws TokenizerException, ParserException, IOException {
        Expr expr;
        if(check(TokenType.ID)){
            if (!lookupTable.contains(currentToken.getStringValue()))
                throw new ParserException("[parsing] identifier "+currentToken.getStringValue()+" was never declared");
            next();
            switch(currentToken.getType()){
                case EQUALS:
                case PLUS_EQUALS:
                case MINUS_EQUALS:
                case STAR_EQUALS:
                case DIVISION_EQUALS:
                case MODULE_EQUALS:
                    next();
                    return assignment(lookupTable);
            }
        }
        tokenizer.prev();
        next();
        expr=logicalOr();
        return expr;
    }

    private Expr logicalOr() throws IOException, TokenizerException {
        Expr expr=logicalAnd();
        return expr;
    }

    private Expr logicalAnd() throws IOException, TokenizerException {
        Expr expr=equality();
        return expr;
    }

    private Expr equality() throws IOException, TokenizerException {
        Expr expr=comparison();
        return expr;
    }

    private Expr comparison() throws IOException, TokenizerException {
        Expr expr=add();
        return expr;
    }

    private Expr add() throws IOException, TokenizerException {
        Expr expr=mult();
        return expr;
    }

    private Expr mult() throws IOException, TokenizerException {
        Expr expr=unary();
        return expr;
    }

    private Expr unary() throws IOException, TokenizerException {
        Expr expr=null;
        switch(currentToken.getType()) {
            case PLUS:
            case MINUS:
            case NOT:
                next();
                expr=unary();
        }
        expr=postfix();
        return expr;
    }

    private Expr postfix() throws IOException, TokenizerException {
        Expr expr=primary();
        return expr;
    }

    private Expr primary() throws IOException, TokenizerException {
        switch(currentToken.getType()){
            case NUM:
                return num();
            case FALSE:
            case TRUE:
                return bool();
        }
        return null;
    }

    private BoolExpr bool() throws IOException, TokenizerException {
        BoolExpr realBool=new BoolExpr(currentToken.getType() == TokenType.TRUE);
        next();
        return realBool;
    }

    private NumExpr num() throws IOException, TokenizerException {
         NumExpr realNum=new NumExpr(currentToken.getValue());
         next();
         return realNum;
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
        while(currentToken.getType()==TokenType.ID) {
            ids.add(id());
            next();
        }
        return ids;
    }

    private String id() throws ParserException{
        check(TokenType.ID, "identifer expected");
        return currentToken.getStringValue();
    }

    private void next() throws IOException, TokenizerException {
        pastToken=currentToken;
        currentToken=tokenizer.next();
    }

    private boolean check(TokenType type) {
        return currentToken.getType()==type;
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