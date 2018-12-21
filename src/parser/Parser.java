package parser;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
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

    public Expr parse() throws IOException, TokenizerException, ParserException, InterpreterException {
        next();
        return program();
    }

    // program ::= function Eos
    private Expr program() throws IOException, TokenizerException, ParserException, InterpreterException {
        Expr expr=function(null);
        check(TokenType.EOS, "no valid end of stream found");


        // DEBUG
        // return expr;

        // need to run the actual code
        // invoke the outer closure
        return new InvokeExpr(expr, new ExprList(new ArrayList<>(0))).eval(null);
    }

    // function ::= "{" optParams optLocals optSequence "}"
    private Expr function(Scope scope) throws IOException, TokenizerException, ParserException {
        checkAndNext(TokenType.OPEN_CURLY_BRACKET, "open '{' error");
        List<String> params=optParams();
        List<String> locals=optLocals();
        List<String> all=new ArrayList<>();
        all.addAll(params);
        all.addAll(locals);

        FunExpr funExpr=new FunExpr(params, locals, optSequence(new Scope(all, scope)));
        checkAndNext(TokenType.CLOSE_CURLY_BRACKET, "close '}' error, found "+currentToken.getType());
        return funExpr;
    }

    // optSequence ::= ( "->" sequence )?
    private Expr optSequence(Scope scope) throws ParserException, IOException, TokenizerException {
        // check for arrow -> if none is provided the program is empty
        // and will return Nil
        if(!check(TokenType.ARROW)){
            return NilVal.instance();
        }
        next();
        return sequence(scope);
    }

    // sequence ::= optAssignment ( ";" optAssignment )*
    private Expr sequence(Scope scope) throws IOException, TokenizerException, ParserException {
        List<Expr> exprs=new ArrayList<>();

        //case with one assignment (no semicolon end)
        Expr internalExpr=optAssignment(scope);
        if(internalExpr!=null) {
            exprs.add(internalExpr);
        }

        //more assignments
        while(currentToken.getType()==TokenType.SEMICOLON){
            next();
            Expr expr=optAssignment(scope);
            if(expr!=null) {
                exprs.add(expr);
            }
        }
        return exprs.size()==0?NilVal.instance():new SeqExpr(new ExprList(exprs));
    }

    // optAssignment ::= assignment?
    private Expr optAssignment(Scope scope) throws IOException, ParserException, TokenizerException {
        // TODO: possible not an assignment?
        return assignment(scope);
    }

    // assignment ::= Id ( "=" | "+=" | "-=" | "*=" | "/=" | "%=" ) assignment
    //	            | logicalOr
    private Expr assignment(Scope scope) throws TokenizerException, ParserException, IOException {
        if(check(TokenType.ID)){
            String currentId=currentToken.getStringValue();
            if (!scope.contains(currentId))
                throw new ParserException("[parsing] identifier "+currentToken.getStringValue()+" was never declared");
            next();
            TokenType op=currentToken.getType();
            switch(op){
                case EQUALS:
                case PLUS_EQUALS:
                case MINUS_EQUALS:
                case STAR_EQUALS:
                case DIVISION_EQUALS:
                case MODULE_EQUALS:
                    next();
                    return new SetVarExpr(currentId, assignment(scope), op);
                default:
                    prev();
            }
        }

        return logicalOr(scope);
    }

    // logicalOr ::= logicalAnd ( "||" logicalOr )?
    private Expr logicalOr(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=logicalAnd(scope);
        if(check(TokenType.OR)){
            next();
            // binary expr contains left and right structure
            expr=new BinaryExpr(expr, logicalOr(scope), TokenType.OR);
        }
        return expr;
    }

    // logicalAnd ::= equality ( "&&" logicalAnd )?
    private Expr logicalAnd(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=equality(scope);
        if(check(TokenType.AND)){
            next();
            expr=new BinaryExpr(expr, logicalAnd(scope), TokenType.AND);
        }
        return expr;
    }

    // equality ::= comparison ( ( "==" | "!=" ) comparison )?
    private Expr equality(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=comparison(scope);
        switch(currentToken.getType()){
            case COMPARISON:
            case DIFFERENCE:
                TokenType type=currentToken.getType();
                next();
                expr=new BinaryExpr(expr, comparison(scope), type);
        }
        return expr;
    }

    // comparison ::= add ( ( "<" | "<=" | ">" | ">=" ) add )?
    private Expr comparison(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=add(scope);
        switch(currentToken.getType()){
            case LESS:
            case LESS_EQUALS:
            case GREATER:
            case GREATER_EQUALS:
                TokenType type=currentToken.getType();
                next();
                expr=new BinaryExpr(expr, add(scope), type);
        }
        return expr;
    }

    // add ::= mult ( ( "+" | "-" ) mult )*
    private Expr add(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=mult(scope);
        while(check(TokenType.PLUS) || check(TokenType.MINUS)){
            TokenType type=currentToken.getType();
            next();
            expr=new BinaryExpr(expr, mult(scope), type);
        }
        return expr;
    }

    // mult ::= unary ( ( "*" | "/" | "%" ) unary )*
    private Expr mult(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=unary(scope);
        while(isMult()) {
            TokenType type = currentToken.getType();
            next();
            expr = new BinaryExpr(expr, unary(scope), type);
        }
        return expr;
    }

    // unary ::= ( "+" | "-" | "!" ) unary
    //	        | postfix
    private Expr unary(Scope scope) throws IOException, TokenizerException, ParserException {
        TokenType op=currentToken.getType();
        switch(op) {
            case PLUS:
            case MINUS:
            case NOT:
                next();
                return new UnaryExpr(op, unary(scope));
        }
        return postfix(scope);
    }

    // postfix ::= primary args*
    private Expr postfix(Scope scope) throws IOException, TokenizerException, ParserException {
        Expr expr=primary(scope);
        while(check(TokenType.OPEN_ROUND_BRACKET)){
            expr=new InvokeExpr(expr, args(scope));
        }
        return expr;
    }

    // args ::= "(" ( sequence ( "," sequence )* )? ")"
    private ExprList args(Scope scope) throws TokenizerException, ParserException, IOException {
        List<Expr> sequence=new ArrayList<>();
        checkAndNext(TokenType.OPEN_ROUND_BRACKET,"expected (");
        if(!check(TokenType.CLOSE_ROUND_BRACKET)) {
            sequence.add(sequence(scope));

            while (check(TokenType.COMMA)) {
                next();
                sequence.add(sequence(scope));
            }
        }
        checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "expected )");

        return new ExprList(sequence);
    }

    // primary ::= num | bool | nil | string
    //	            | getId
    //	            | function
    //	            | subsequence
    //	            | cond
    //	            | loop
    //	            | print
    private Expr primary(Scope scope) throws IOException, TokenizerException, ParserException {
        switch(currentToken.getType()){
            case NUM:
                return num();
            case FALSE:
            case TRUE:
                return bool();
            case NIL:
                return nil();
            case STRING:
                return string();
            case ID:
                return getId(scope);
            case OPEN_CURLY_BRACKET:
                // return new InvokeExpr(function(scope))
                return function(scope);
            case OPEN_ROUND_BRACKET:
                return subsequence(scope);
            case IF:
            case IFNOT:
                return cond(scope);
            case WHILE:
            case WHILENOT:
                return loop(scope);
            case PRINT:
            case PRINTLN:
                return print(scope);
        }
        return null;
    }

    // subsequence ::= "(" sequence ")"
    private Expr subsequence(Scope scope) throws TokenizerException, ParserException, IOException {
        checkAndNext(TokenType.OPEN_ROUND_BRACKET, "Expected '('");
        Expr expr=sequence(scope);
        checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "Expected ')'");
        return expr;
    }

    private GetVarExpr getId(Scope scope) throws IOException, TokenizerException {
        String id=currentToken.getStringValue();
        scope.contains(id);
        next();
        return new GetVarExpr(id);
    }

    private PrintExpr print(Scope scope) throws IOException, ParserException, TokenizerException {
        TokenType type=currentToken.getType();
        next();
        // TODO: case args return null!
        return new PrintExpr(args(scope), type);
    }

    private WhileExpr loop(Scope scope) throws IOException, TokenizerException, ParserException {
        boolean invertedLogic=check(TokenType.WHILENOT);
        boolean mustClose=false;
        next();
        if(check(TokenType.OPEN_ROUND_BRACKET)) {
            next();
            mustClose=true;
        }
        Expr whileCondition=sequence(scope);
        if(mustClose){
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "A Round parenthesis was opened but never closed");
        }

        Expr doActions=null;
        if(check(TokenType.DO)){
            next();
            doActions=sequence(scope);
        }
        WhileExpr realLoop=new WhileExpr(invertedLogic, whileCondition, doActions);
        checkAndNext(TokenType.OD, "expected od to end while statement, found "+currentToken.getType()+" "+currentToken.getStringValue());
        return realLoop;
    }

    private IfExpr cond(Scope scope) throws IOException, TokenizerException, ParserException {
        boolean invertedLogic=check(TokenType.IFNOT);
        boolean mustClose=false;
        next();
        if(check(TokenType.OPEN_ROUND_BRACKET)) {
            next();
            mustClose=true;
        }
        Expr condition=sequence(scope);
        if(mustClose){
            checkAndNext(TokenType.CLOSE_ROUND_BRACKET, "A Round parenthesis was opened but never closed");
        }

        checkAndNext(TokenType.THEN, "expected then after if statement");
        Expr ifActions=sequence(scope);
        Expr elseActions=null;
        if(check(TokenType.ELSE)){
            next();
            elseActions=sequence(scope);
        }
        IfExpr realCondition=new IfExpr(invertedLogic, condition, ifActions, elseActions);
        checkAndNext(TokenType.FI, "expected fi to end if statement, found "+currentToken.getStringValue());
        return realCondition;
    }

    private NilVal nil() throws IOException, TokenizerException {
        next();
        return NilVal.instance();
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
        // System.out.println("CONSUMED: "+currentToken.getType()+" "+currentToken.getStringValue());
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

    private void prev() throws TokenizerException {
        tokenizer.prev();
        currentToken=pastToken;
        //next();
    }
}