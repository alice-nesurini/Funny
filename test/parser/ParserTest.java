package parser;

import org.junit.jupiter.api.Test;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

public class ParserTest {

    @Test
    public void testOneSequence() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=2}"));
        new Parser(tokenizer).parse();
    }

    @Test
    public void testOptLocal() throws IOException, TokenizerException, ParserException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{myVar param id ->}"));
        new Parser(tokenizer).parse();
    }

    @Test
    public void testOptParam() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{(myVar param id other) ->}"));
        new Parser(tokenizer).parse();
    }
}
