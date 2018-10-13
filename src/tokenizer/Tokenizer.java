package tokenizer;

import java.io.IOException;
import java.io.Reader;
//TODO: comments
//TODO: numbers
//TODO: if/while... construct
public class Tokenizer{
    private final Reader reader;
    private int currentChar;

    public Tokenizer(Reader reader){
        this.reader=reader;
    }

    public Token next() throws IOException {
        skipSpaces();
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
                return not();
            case '-':
                //3 possible cases - or -= or ->
                return minus();
            case '+':
                //also 2 cases + or +=
                return plus();
            case '=':
                //could be = or ==
                return equal();
            case '/':
                return div();
            case '*':
                return star();
            case '&':
                return and();
            case '|':
                return or();
            case '<':
                return checkLess();
            case '>':
                return checkGrater();
            case -1:
                return new Token(TokenType.EOS);
            case '"':
                //case string
                return checkString();
            case 'p':
                //var or print or println
                return checkPrint();
        }
        //could be a string
        //constructor var name or number
        if(Character.isAlphabetic(currentChar)){
            return checkId();
        }
        return new Token(TokenType.UNKNOWN);
    }

    private Token checkId() throws IOException {
        StringBuilder wordBuilder=new StringBuilder();
        while(!Character.isWhitespace(currentChar) &&
                currentChar!=',' &&
                currentChar!='='){
            wordBuilder.append((char)currentChar);
            reader.mark(1);
            currentChar=reader.read();
        }
        reader.reset();
        return new Token(TokenType.ID, wordBuilder.toString());
    }

    private Token checkString() throws IOException {
        StringBuilder wordBuilder=new StringBuilder();
        while((currentChar=reader.read())!='"'){
            wordBuilder.append((char)currentChar);
        }

        return new Token(TokenType.STRING, wordBuilder.toString());
    }

    protected Token checkPrint() throws IOException {
        StringBuilder wordBuilder=new StringBuilder();
        while(!Character.isWhitespace(currentChar) &&
                currentChar!='(' &&
                currentChar!='='){
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

    private Token equal() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.COMPARISON);
        reader.reset();
        return new Token(TokenType.EQUALS);
    }

    private Token not() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.DIFFERENCE);
        reader.reset();
        return new Token(TokenType.NOT);
    }

    private Token star() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.STAR);
        reader.reset();
        return new Token(TokenType.STAR);
    }

    private Token div() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.DIVISION);
        reader.reset();
        return new Token(TokenType.DIVISION);
    }

    private Token and() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='&') return new Token(TokenType.AND);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token or() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='|') return new Token(TokenType.OR);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token plus() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='=') return new Token(TokenType.PLUS_EQUALS);
        reader.reset();
        return new Token(TokenType.PLUS);
    }

    private Token minus() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(currentChar=='>') return new Token(TokenType.ARROW);
        if(currentChar=='=') return new Token(TokenType.MINUS_EQUALS);
        reader.reset();
        return new Token(TokenType.MINUS);
    }

    private void skipSpaces() throws IOException {
        reader.mark(1);
        currentChar=reader.read();
        if(Character.isWhitespace(currentChar)) {
            skipSpaces();
        }
        reader.reset();
    }
}