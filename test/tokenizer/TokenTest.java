package tokenizer;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import structure.Expr;
import structure.InterpreterException;
import structure.Launcher;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenTest{
    @Test
    public void testNextPrint() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("\n { ->\n" +
                "\tprint(\"Hello, world!\\n\");\n" +
                "\tprint(\"Hi!\", \"\\n\");\n" +
                " } "));

        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("Hello, world!\\n", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("Hi!", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("\\n", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testNextParam() throws IOException, TokenizerException {
        //final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("myProgram.funny")));
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ whileing, param ->\n" +
                "    whileing=\"a string\";\n" +
                "}"));
        Token runnerToken;

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("whileing", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("whileing", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("a string", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testNextNumber() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("myProgram.funny")));

        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("result", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal("2.3"), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        // assertEquals(new BigDecimal(7.51), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("result", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.PLUS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.IF, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("result", runnerToken.getStringValue());
        assertEquals(TokenType.COMPARISON, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());

        assertEquals(TokenType.THEN, tokenizer.next().getType());
        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("The result: ", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("result", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.FI, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testNextLamdba() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("fibonacci.funny")));

        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("n", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib0", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib0", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(1), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(0), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.WHILE, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("n", runnerToken.getStringValue());
        assertEquals(TokenType.GREATER, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(0), runnerToken.getValue());
        assertEquals(TokenType.DO, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib0", runnerToken.getStringValue());
        assertEquals(TokenType.PLUS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib0", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("n", runnerToken.getStringValue());
        assertEquals(TokenType.MINUS_EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(1), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.OD, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib1", runnerToken.getStringValue());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINTLN, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("fib", runnerToken.getStringValue());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(10000), runnerToken.getValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testCommentException() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ ->\n" +
                "    //inline\n" +
                "    /* error\n" +
                "}"));
        //Expectiing excpetion from tokenizer
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertThrows(TokenizerException.class, tokenizer::next);
    }

    @Test
    public void testNumberFormat() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ myVar ->\n" +
                "    myVar = -1;\n" +
                "    myVar = 1e-30;\n" +
                "    myVar = .7;\n" +
                "}"));
        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        assertEquals("myVar", tokenizer.next().getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.MINUS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        assertEquals(new BigDecimal(1), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals("myVar", tokenizer.next().getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        //assertEquals(new BigDecimal(1e-30), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals("myVar", tokenizer.next().getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        //assertEquals(new BigDecimal(0.7), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testLambdaCode() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{_true, _false, $if ->\n" +
                "    _true = {(t, f) -> t};\n" +
                "    _false = {(t, f) -> f};\n" +
                "    $if = {(c, t, e) -> c(t, e)()};\n" +
                "    \n" +
                "    println($if(_false, {-> while true do {} od}, {-> \"False\"}))\n" +
                "}"));
        Token runnerToken;

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("_true", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("_false", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("$if", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("_true", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("t", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("f", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("t", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("_false", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("t", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("f", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("f", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("$if", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("c", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("t", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("e", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("c", runnerToken.getStringValue());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("t", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("e", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINTLN, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("$if", runnerToken.getStringValue());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("_false", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.WHILE, tokenizer.next().getType());
        assertEquals(TokenType.TRUE, tokenizer.next().getType());
        assertEquals(TokenType.DO, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OD, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("False", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testUnicode() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ ->\n" +
                "\tprint(\"Hello, world!\\n\");\n" +
                "\tprint(\"Hi!\", \"\\n\");\n" +
                "\tprintln(\"你好\");\n" +
                "}"));

        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("Hello, world!\\n", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("Hi!", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("\\n", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINTLN, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("你好", runnerToken.getStringValue());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testStringNeverEnds() throws IOException, TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{ ->\n" +
                "\tprint(\"No end);\n" +
                "}"));

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertThrows(TokenizerException.class, tokenizer::next);
    }

    @Test
    public void testPrev() throws IOException, TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{ ->\n" +
                "}"));

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        tokenizer.prev();
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testExtendedPrev() throws IOException, TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{ ->\n" +
                "}"));

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        tokenizer.prev();
        assertThrows(TokenizerException.class, tokenizer::prev);
    }

    @Test
    public void testingMinus() throws IOException, TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{a->a=5;a=a-5+2;print(\"a: \", a)}"));
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.MINUS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.PLUS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());
        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.STRING, tokenizer.next().getType());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void completeTesting() throws IOException, TokenizerException {
        final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("complete.funny")));
        while(tokenizer.next().getType()!=TokenType.EOS){}
    }

    @Test
    public void makeCounter() throws IOException, TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{makeCounter yourCounter n ->\n" +
                "    makeCounter = {(balance) ->\n" +
                "        {(amount) -> balance += amount}\n" +
                "    };\n" +
                "    yourCounter = makeCounter(50);\n" +
                "    print(yourCounter(-10));\n" +
                "}"));

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.PLUS_EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());
        assertEquals(TokenType.PRINT, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.MINUS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
    }

    @Test
    void fibonacciRicorsive() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{fib ->\n" +
                "    fib = {(n) ->\n" +
                "        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
                "    };\n" +
                "    \n" +
                "    println(fib(40))\n" +
                "}"));
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.IF, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.LESS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.THEN, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.ELSE, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.MINUS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.PLUS, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.MINUS, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.FI, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.PRINTLN, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ID, tokenizer.next().getType());
        assertEquals(TokenType.OPEN_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.NUM, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_ROUND_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void commentTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("" +
                "/* c1 */\n" +
                "/* c2 */\n" +
                "{->}"));
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());
        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }
}
