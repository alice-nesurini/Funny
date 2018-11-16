package parser;

import structure.*;
import structure.BoolVal;
import structure.NumVal;
import structure.StringVal;
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

    public SeqExpr parse() throws IOException, TokenizerException, ParserException {
        next();
        return program();
    }

    private SeqExpr program() throws IOException, TokenizerException, ParserException {
        SeqExpr expr=function();
        check(TokenType.EOS, "no valid end of stream found");
        return expr;
    }

    private SeqExpr function() throws IOException, TokenizerException, ParserException {
        checkAndNext(TokenType.OPEN_CURLY_BRACKET, "open '{' error");
        List<String> params=optParams();
        List<String> locals=optLocals();
        params.addAll(locals);

        LookupTable lookupTable=new LookupTable(params, null);
        SeqExpr expr=optSequence(lookupTable);
        checkAndNext(TokenType.CLOSE_CURLY_BRACKET, "close '}' error");
        return expr;
    }

    private SeqExpr optSequence(LookupTable lookupTable) throws ParserException, IOException, TokenizerException {
        checkAndNext(TokenType.ARROW, "starting ARROW (->) was expected");
        return sequence(lookupTable);
    }

    private SeqExpr sequence(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
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
        return exprs.size()==0?null:new SeqExpr(exprs);
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
        expr=logicalOr(lookupTable);
        return expr;
    }

    private Expr logicalOr(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=logicalAnd(lookupTable);
        if(check(TokenType.OR)){
            next();
            // binary expr contains left and right structure
            expr=new BinaryExpr(expr, logicalOr(lookupTable), TokenType.OR);
        }
        return expr;
    }

    private Expr logicalAnd(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=equality(lookupTable);
        if(check(TokenType.AND)){
            next();
            expr=new BinaryExpr(expr, logicalAnd(lookupTable), TokenType.AND);
        }
        return expr;
    }

    private Expr equality(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=comparison(lookupTable);
        switch(currentToken.getType()){
            case COMPARISON:
            case DIFFERENCE:
                TokenType type=currentToken.getType();
                System.out.println("EQ. "+type);
                next();
                expr=new BinaryExpr(expr, comparison(lookupTable), type);
        }
        return expr;
    }

    private Expr comparison(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=add(lookupTable);
        switch(currentToken.getType()){
            case LESS:
            case LESS_EQUALS:
            case GREATER:
            case GREATER_EQUALS:
                TokenType type=currentToken.getType();
                System.out.println("COMP. "+type);
                next();
                expr=new BinaryExpr(expr, add(lookupTable), type);
        }
        return expr;
    }

    private Expr add(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=mult(lookupTable);
        switch(currentToken.getType()){
            case PLUS:
            case MINUS:
                TokenType type=currentToken.getType();
                System.out.println("ADD. "+type);
                next();
                expr=new BinaryExpr(expr, mult(lookupTable), type);
        }
        return expr;
    }

    private Expr mult(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=unary(lookupTable);
        while(isMult()) {
            TokenType type = currentToken.getType();
            System.out.println("MULT. " + type);
            next();
            expr = new BinaryExpr(expr, unary(lookupTable), type);
        }
        return expr;
    }

    private Expr unary(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=null;
        switch(currentToken.getType()) {
            case PLUS:
            case MINUS:
            case NOT:
                next();
                System.out.println("UNARY. " + currentToken.getType());
                expr=unary(lookupTable);
        }
        expr=postfix(lookupTable);
        return expr;
    }

    private Expr postfix(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        Expr expr=primary(lookupTable);
        // TODO: POSTFIX
        //args(lookupTable);
        // TODO: args? giusto? 0-*
        return expr;
    }

    private Expr primary(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        switch(currentToken.getType()){
            case NUM:
                return num();
            case FALSE:
            case TRUE:
                return bool();
            case NIL:
                return null;
                //return nil();
            case STRING:
                return string();
            case ID:
                return null;
                //return getId();
            case OPEN_CURLY_BRACKET:
                return function();
            case OPEN_ROUND_BRACKET:
                return null;
                //return subsequence();
            case IF:
            case IFNOT:
                System.out.println("IF STM. "+currentToken.getType());
                return cond(lookupTable);
            case WHILE:
            case WHILENOT:
                System.out.println("WHILE STM. "+currentToken.getType());
                return loop(lookupTable);
            case PRINT:
            case PRINTLN:
                return print(lookupTable);
        }
        return null;
    }

    private PrintExpr print(LookupTable lookupTable) throws IOException, ParserException, TokenizerException {
        return new PrintExpr(args(lookupTable));
    }

    private ExprList args(LookupTable lookupTable) throws TokenizerException, ParserException, IOException {
        SeqExpr exprSeq=null;
        // added
        next();
        // TODO: maybe wrong?
        checkAndNext(TokenType.OPEN_ROUND_BRACKET,"expected (");
        if(!check(TokenType.CLOSE_ROUND_BRACKET)) {
            exprSeq=sequence(lookupTable);
            System.out.println("args "+currentToken.getType());
            while(check(TokenType.COMMA)){
                next();
                // TODO: expr sequence append? more string!!
                // missing case more string
                exprSeq.add(sequence(lookupTable));
            }
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "Expected )");
        }
        return new ExprList(exprSeq.getExprs());
    }

    private WhileExpr loop(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        boolean invertedLogic=check(TokenType.WHILENOT);
        boolean mustClose=false;
        next();
        if(check(TokenType.OPEN_ROUND_BRACKET)) {
            next();
            mustClose=true;
        }
        Expr whileCondition=sequence(lookupTable);
        if(mustClose){
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "A Round parenthesis was opened but never closed");
        }

        Expr doActions=null;
        if(check(TokenType.DO)){
            next();
            doActions=sequence(lookupTable);
        }
        WhileExpr realLoop=new WhileExpr(invertedLogic, whileCondition, doActions);
        checkAndNext(TokenType.OD, "expected od to end while statement, found "+currentToken.getType()+" "+currentToken.getStringValue());
        return realLoop;
    }

    private IfExpr cond(LookupTable lookupTable) throws IOException, TokenizerException, ParserException {
        boolean invertedLogic=check(TokenType.IFNOT);
        boolean mustClose=false;
        next();
        if(check(TokenType.OPEN_ROUND_BRACKET)) {
            next();
            mustClose=true;
        }
        Expr condition=sequence(lookupTable);
        if(mustClose){
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "A Round parenthesis was opened but never closed");
        }

        checkAndNext(TokenType.THEN, "expected then after if statement");
        Expr ifActions=sequence(lookupTable);
        Expr elseActions=null;
        if(check(TokenType.ELSE)){
            elseActions=sequence(lookupTable);
        }
        IfExpr realCondition=new IfExpr(invertedLogic, condition, ifActions, elseActions);
        checkAndNext(TokenType.FI, "expected fi to end if statement");
        return realCondition;
    }

    private StringVal string() throws IOException, TokenizerException {
        StringVal realString=new StringVal(currentToken.getStringValue());
        next();
        return realString;
    }


    private BoolVal bool() throws IOException, TokenizerException {
        BoolVal realBool=new BoolVal(currentToken.getType() == TokenType.TRUE);
        next();
        return realBool;
    }

    private NumVal num() throws IOException, TokenizerException {
         NumVal realNum=new NumVal(currentToken.getValue());
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
    private boolean isMult() {
        switch (currentToken.getType()) {
            case STAR:
            case DIVISION:
            case MODULE:
                return true;
            default:
                return false;
        }
    }
}