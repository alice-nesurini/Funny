import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import structure.*;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

public class InterpreterTest {
    @Test
    public void simplePrintTest() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\");print(\"Other Hello\")}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(new Env(new Frame(null, null, null), null));
    }


    @Test
    public void multiPrintTest() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\",\"Test\", \"java\");}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(new Env(new Frame(null, null, null), null));
    }

    @Test
    public void simpleNumAssign() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{param->param=2+7;print(param)}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(new Env(new Frame(null, null, null), null));
    }
}
