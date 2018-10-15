package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

//TODO: comments
//TODO: MODULE missing
public class Tokenizer{
    private final Reader reader;
    private int currentChar;

    public Tokenizer(Reader reader){
        this.reader=reader;
    }

    public Token next() throws IOException {
        skipSpacesAndComment();
        //checkComment();

        currentChar=reader.read();
        switch(currentChar){
            case '{':
                return new Token(TokenType.OPEN_CURLY_BRACKET);
            case '}':
                return new Token(TokenType.CLOSE_CURLY_BRACKET);
            case '(':
                return new Token(TokenType.OPEN_ROUND_BRACKET);
            case ')':
                return new Token(TokenType.CLOSE_ROUND_BRACKET);
            case '[':
                return new Token(TokenType.OPEN_SQUARE_BRACKET);
            case ']':
                return new Token(TokenType.CLOSE_SQUARE_BRACKET);
            case ';':
                return new Token(TokenType.SEMICOLON);
            case ',':
                return new Token(TokenType.COMMA);
            case '!':
                //2 cases ! or !=
                return checkNot();
            case '-':
                //3 possible cases - or -= or ->
                return checkMinus();
            case '+':
                //also 2 cases + or +=
                return checkPlus();
            case '=':
                //could be = or ==
                return checkEqual();
            case '/':
                //comment checked before
                return checkDiv();
            case '*':
                return checkStar();
            case '&':
                return checkAnd();
            case '|':
                return checkOr();
            case '<':
                return checkLess();
            case '>':
                return checkGrater();
            case -1:
                return new Token(TokenType.EOS);
            case '"':
                //case string
                return checkString();
        }
        if(Character.isAlphabetic(currentChar)){
            return checkPrint();
        }

        if(Character.isDigit(currentChar)){
            return checkDigit();
        }
        return new Token(TokenType.UNKNOWN);
    }

    //TODO: lost precision
    private Token checkDigit() throws IOException {
        StringBuilder numberBuilder=new StringBuilder();
        while(!isSymbol(currentChar) &&
                !Character.isWhitespace(currentChar)){
            numberBuilder.append((char)currentChar);
            reader.mark(1);
            currentChar=reader.read();
        }
        reader.reset();
        return new Token(TokenType.NUM, new BigDecimal(numberBuilder.toString()));
    }

    private Token checkString() throws IOException {
        StringBuilder wordBuilder=new StringBuilder();
        while((currentChar=reader.read())!='"'){
            wordBuilder.append((char)currentChar);
        }
        return new Token(TokenType.STRING, wordBuilder.toString());
    }

    private Token checkPrint() throws IOException {
        StringBuilder wordBuilder=new StringBuilder();
        while(!Character.isWhitespace(currentChar) &&
                !isSymbol(currentChar)){
            reader.mark(1);
            wordBuilder.append((char)currentChar);
            currentChar=reader.read();
        }
        reader.reset();

        if(wordBuilder.toString().equals("print")){
            return new Token(TokenType.PRINT);
        }
        if(wordBuilder.toString().equals("println")){
            return new Token(TokenType.PRINTLN);
        }
        if(wordBuilder.toString().equals("if")){
            return new Token(TokenType.IF);
        }
        if(wordBuilder.toString().equals("ifnot")){
            return new Token(TokenType.IFNOT);
        }
        if(wordBuilder.toString().equals("fi")){
            return new Token(TokenType.FI);
        }
        if(wordBuilder.toString().equals("then")){
            return new Token(TokenType.THEN);
        }
        if(wordBuilder.toString().equals("else")){
            return new Token(TokenType.ELSE);
        }
        if(wordBuilder.toString().equals("while")){
            return new Token(TokenType.WHILE);
        }
        if(wordBuilder.toString().equals("whilenot")){
            return new Token(TokenType.WHILENOT);
        }
        if(wordBuilder.toString().equals("do")){
            return new Token(TokenType.DO);
        }
        if(wordBuilder.toString().equals("od")){
            return new Token(TokenType.OD);
        }
        if(wordBuilder.toString().equals("nil")){
            return new Token(TokenType.NIL);
        }
        if(wordBuilder.toString().equals("false")){
            return new Token(TokenType.FALSE);
        }
        if(wordBuilder.toString().equals("true")){
            return new Token(TokenType.TRUE);
        }
        return new Token(TokenType.ID, wordBuilder.toString());
    }

    private Token checkGrater() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.GREATER_EQUALS);
        reader.reset();
        return new Token(TokenType.GREATER);
    }

    private Token checkLess() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.LESS_EQUALS);
        reader.reset();
        return new Token(TokenType.LESS);
    }

    private Token checkEqual() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.COMPARISON);
        reader.reset();
        return new Token(TokenType.EQUALS);
    }

    private Token checkNot() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.DIFFERENCE);
        reader.reset();
        return new Token(TokenType.NOT);
    }

    private Token checkStar() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.STAR_EQUALS);
        reader.reset();
        return new Token(TokenType.STAR);
    }

    private Token checkDiv() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.DIVISION_EQUALS);
        reader.reset();
        return new Token(TokenType.DIVISION);
    }

    //TODO: refactor this...
    //TODO: comment inside comment
    private void checkComment() throws IOException {
        StringBuilder commentBuilder=new StringBuilder();
        int numClose=2;
        while(!commentBuilder.toString().contains("*/") &&
                numClose!=0){
            currentChar=reader.read();
            commentBuilder.append((char)currentChar);
            if(commentBuilder.toString().contains("*/")){
                System.out.println(commentBuilder.toString());
                numClose--;
                commentBuilder=new StringBuilder();
            }
        }
    }

    private Token checkAnd() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='&') return new Token(TokenType.AND);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token checkOr() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='|') return new Token(TokenType.OR);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token checkPlus() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.PLUS_EQUALS);
        reader.reset();
        return new Token(TokenType.PLUS);
    }

    private Token checkMinus() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='>') return new Token(TokenType.ARROW);
        if(currentChar=='=') return new Token(TokenType.MINUS_EQUALS);
        reader.reset();
        return new Token(TokenType.MINUS);
    }

    private void skipSpacesAndComment() throws IOException {
        reader.mark(2);
        currentChar=reader.read();
        if(Character.isWhitespace(currentChar)) {
            skipSpacesAndComment();
        }
        if(currentChar=='/'){
            //commento?
            currentChar=reader.read();
            if(currentChar=='*'){
                //si
                System.out.println("comment");
            }
            reader.reset();
        }
        reader.reset();
    }

    private boolean isSymbol(int symbol){
        switch(symbol){
            case '+':
            case '-':
            case '/':
            case '%':
            case '*':
            case '=':
            case ';':
            case ',':
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }
}