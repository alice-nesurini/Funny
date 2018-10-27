package parser;

import org.junit.jupiter.api.Test;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

public class ParserTest {

    @Test
    public void testParam() throws IOException, TokenizerException, ParserException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{myVar param id ->}"));
        Parser parser=new Parser(tokenizer);
    }
}
