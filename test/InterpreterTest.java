import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import structure.SeqExpr;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

public class InterpreterTest {
    @Test
    public void simpleAssignTest() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=2}"));
        SeqExpr expr=new Parser(tokenizer).parse();
    }
}
