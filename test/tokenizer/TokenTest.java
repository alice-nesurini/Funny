package tokenizer;

import org.junit.jupiter.api.Test;

import java.io.*;

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
}
