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
    public void simplePrintTest() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\");print(\"Other Hello\")}"));
        SeqExpr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }


    @Test
    public void multiPrintTest() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\",\"Test\");}"));
        SeqExpr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleNumAssign() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{param->param=2+2;}"));
        SeqExpr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }
}
