package structure;

import parser.Parser;
import parser.ParserException;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;

public class Launcher {

    public static void launch(Tokenizer tokenizer) throws TokenizerException, ParserException, InterpreterException, IOException {
        new Parser(tokenizer).parse();
    }
}
