package tokenizer;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest{

    @Test
    public void testNextPrint() throws IOException {
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
    public void testNextParam() throws IOException {
        //final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("myProgram.funny")));
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ myVar, param ->\n" +
                "    myVar=\"a string\";\n" +
                "}"));
        Token runnerToken;

        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.ARROW, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.STRING, runnerToken.getType());
        assertEquals("a string", runnerToken.getStringValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        assertEquals(TokenType.CLOSE_CURLY_BRACKET, tokenizer.next().getType());
    }

    @Test
    public void testNextNumber() throws IOException {
        final Tokenizer tokenizer=new Tokenizer(new BufferedReader(new FileReader("myProgram.funny")));

        Token runnerToken;
        assertEquals(TokenType.OPEN_CURLY_BRACKET, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("myVar", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.COMMA, tokenizer.next().getType());
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
        // TODO: failing assertion BigDecimal 2.999999...!=2.3
        // assertEquals(new BigDecimal(2.3), runnerToken.getValue());
        assertEquals(TokenType.SEMICOLON, tokenizer.next().getType());

        runnerToken=tokenizer.next();
        assertEquals(TokenType.ID, runnerToken.getType());
        assertEquals("param", runnerToken.getStringValue());
        assertEquals(TokenType.EQUALS, tokenizer.next().getType());
        runnerToken=tokenizer.next();
        assertEquals(TokenType.NUM, runnerToken.getType());
        // TODO: failing assertion BigDecimal 7.509999...!=5.21
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
    public void testNextLamdba() throws IOException {
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
}
