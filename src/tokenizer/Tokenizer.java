package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

public class Tokenizer {
    private boolean backtrack = false;
    private Token currentToken;
    private final Reader reader;
    private int currentChar;

    public Tokenizer(Reader reader) {
        this.reader = reader;
    }

    public Token next() throws IOException, TokenizerException {
        if (backtrack) {
            backtrack = false;
            return currentToken;
        }
        currentToken = internalNext();
        return currentToken;
    }

    private Token internalNext() throws IOException, TokenizerException {

        skipGarbage();

        currentChar = reader.read();

        switch (currentChar) {
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
            case '%':
                return checkModule();
            case '!':
                //2 cases ! or !=
                return checkNot();
            case '.':
                return checkDigit(false);
            case '-':
                //case could be NUMBER
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
        if (Character.isJavaIdentifierStart(currentChar)) {
            return checkKeyword();
        }

        if (Character.isDigit(currentChar)) {
            return checkDigit(false);
        }
        return new Token(TokenType.UNKNOWN);
    }

    private void skipGarbage() throws IOException, TokenizerException {
        skipSpaces();

        //skipAllComment(0);
        // to make it work old comment de-comment next two lines
        skipComment();
        skipInline();
    }

    private void skipInline() throws IOException, TokenizerException {
        reader.mark(2);
        // fixed inline multiple line from if to while
        while ((currentChar = reader.read()) == '/') {
            if ((currentChar = reader.read()) == '/') {
                while ((currentChar = reader.read()) != '\n') {
                    // fix or EOS found
                    if(currentChar==-1){
                        break;
                    }
                    reader.mark(1);
                }
            }
        }
        reader.reset();
        skipSpaces();
        skipComment();
    }

    private Token checkDigit(boolean negate) throws IOException {
        StringBuilder numberBuilder = new StringBuilder();
        if (negate) {
            numberBuilder.append("-");
        }

        boolean checkExp = false;
        boolean flagExp = false;
        while ((flagExp || isSymbol(currentChar)) && !Character.isWhitespace(currentChar)) {
            numberBuilder.append((char) currentChar);
            flagExp = false;
            if (!checkExp && numberBuilder.toString().contains("e")) {
                flagExp = true;
                checkExp = true;
            }
            reader.mark(1);
            currentChar = reader.read();
        }
        reader.reset();
        return new Token(TokenType.NUM, new BigDecimal(numberBuilder.toString()));
    }

    private Token checkString() throws IOException, TokenizerException {
        StringBuilder wordBuilder = new StringBuilder();
        while ((currentChar = reader.read()) != '"') {
            wordBuilder.append((char) currentChar);
            if (currentChar == -1) {
                throw new TokenizerException("[string] A string was never closed");
            }
        }
        return new Token(TokenType.STRING, wordBuilder.toString());
    }

    private Token checkKeyword() throws IOException {
        StringBuilder wordBuilder = new StringBuilder();
        while (Character.isJavaIdentifierPart(currentChar)) {
            reader.mark(1);
            wordBuilder.append((char) currentChar);
            currentChar = reader.read();
        }
        reader.reset();

        if (wordBuilder.toString().equals("print")) {
            return new Token(TokenType.PRINT);
        }
        if (wordBuilder.toString().equals("println")) {
            return new Token(TokenType.PRINTLN);
        }
        if (wordBuilder.toString().equals("if")) {
            return new Token(TokenType.IF);
        }
        if (wordBuilder.toString().equals("ifnot")) {
            return new Token(TokenType.IFNOT);
        }
        if (wordBuilder.toString().equals("fi")) {
            return new Token(TokenType.FI);
        }
        if (wordBuilder.toString().equals("then")) {
            return new Token(TokenType.THEN);
        }
        if (wordBuilder.toString().equals("else")) {
            return new Token(TokenType.ELSE);
        }
        if (wordBuilder.toString().equals("while")) {
            return new Token(TokenType.WHILE);
        }
        if (wordBuilder.toString().equals("whilenot")) {
            return new Token(TokenType.WHILENOT);
        }
        if (wordBuilder.toString().equals("do")) {
            return new Token(TokenType.DO);
        }
        if (wordBuilder.toString().equals("od")) {
            return new Token(TokenType.OD);
        }
        if (wordBuilder.toString().equals("nil")) {
            return new Token(TokenType.NIL);
        }
        if (wordBuilder.toString().equals("false")) {
            return new Token(TokenType.FALSE);
        }
        if (wordBuilder.toString().equals("true")) {
            return new Token(TokenType.TRUE);
        }
        return new Token(TokenType.ID, wordBuilder.toString());
    }

    private Token checkGrater() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.GREATER_EQUALS);
        reader.reset();
        return new Token(TokenType.GREATER);
    }

    private Token checkLess() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.LESS_EQUALS);
        reader.reset();
        return new Token(TokenType.LESS);
    }

    private Token checkEqual() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.COMPARISON);
        reader.reset();
        return new Token(TokenType.EQUALS);
    }

    private Token checkNot() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.DIFFERENCE);
        reader.reset();
        return new Token(TokenType.NOT);
    }

    private Token checkStar() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.STAR_EQUALS);
        reader.reset();
        return new Token(TokenType.STAR);
    }

    private Token checkDiv() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.DIVISION_EQUALS);
        reader.reset();
        return new Token(TokenType.DIVISION);
    }

    private Token checkModule() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.MODULE_EQUALS);
        reader.reset();
        return new Token(TokenType.MODULE);
    }

    private void skipComment() throws IOException, TokenizerException {
        StringBuilder commentBuilder = new StringBuilder();
        int numberOfComment = 0;
        reader.mark(2);
        if (reader.read() == '/' && reader.read() == '*') {
            //there is a comment
            numberOfComment++;
            while (numberOfComment != 0) {
                // 2 reader.mark(1);
                //found close comment
                if (commentBuilder.toString().contains("*/")) {
                    numberOfComment--;
                    commentBuilder = new StringBuilder();
                }
                //found open comment
                else if (commentBuilder.toString().contains("/*")) {
                    numberOfComment++;
                    commentBuilder = new StringBuilder();
                }

                // case the comment is never closed
                // case when the tokenizer throws an exception
                currentChar = reader.read();
                if (currentChar == -1) {
                    throw new TokenizerException("[comment] A comment was opened and never closed");
                }
                commentBuilder.append((char) currentChar);
            }
        } else {
            reader.reset();
            //return;
        }
        // if the next reset is executed
        // fail testNextLambda
        //reader.reset();

        // 2 reader.reset();
        skipSpaces();
        //internalNext();
    }

    private void skipAllComment(int nClose) throws IOException {
        reader.mark(2);
        // if is a comment
        if ((currentChar = reader.read()) == '/') {
            if ((currentChar = reader.read()) == '*') {
                // multiline comment
                System.out.println("Read : " + (char) currentChar);
                while (true) {
                    while ((currentChar = reader.read()) != '*') {
                        //System.out.println("Read : " + (char)currentChar);
                    }
                    if ((currentChar = reader.read()) == '/') {
                        // close
                        System.out.println("Close : " + (char) currentChar + " #nClose = " + nClose);
                        skipSpaces();
                        skipAllComment(++nClose);
                        break;
                    } else {
                        continue;
                    }
                }
            } else if (currentChar == '/') {
                // inline comment skip all line
                while ((currentChar = reader.read()) != '\n') {}
                skipAllComment(nClose);

            }
        } else {
            // not a comment, ending
            reader.reset();
        }
    }

    private Token checkAnd() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '&') return new Token(TokenType.AND);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token checkOr() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '|') return new Token(TokenType.OR);
        reader.reset();
        return new Token(TokenType.UNKNOWN);
    }

    private Token checkPlus() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '=') return new Token(TokenType.PLUS_EQUALS);
        reader.reset();
        return new Token(TokenType.PLUS);
    }

    private Token checkMinus() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (currentChar == '>') return new Token(TokenType.ARROW);
        if (currentChar == '=') return new Token(TokenType.MINUS_EQUALS);
        //if(Character.isDigit(currentChar)) return checkDigit(true);
        reader.reset();
        return new Token(TokenType.MINUS);
    }

    private void skipSpaces() throws IOException {
        reader.mark(1);
        currentChar = reader.read();
        if (Character.isWhitespace(currentChar)) {
            skipSpaces();
        }
        reader.reset();
    }

    private boolean isSymbol(int symbol) {
        switch (symbol) {
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
            case '{':
            case '}':
            case '<':
            case '>':
                return false;
            default:
                return true;
        }
    }

    public void prev() throws TokenizerException {
        if (backtrack) {
            throw new TokenizerException("[prev] can only backtrack one token");
        }
        backtrack = true;
    }
}