import parser.ParserException;
import structure.InterpreterException;
import structure.Launcher;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException, TokenizerException, ParserException, InterpreterException {
        Launcher.launch(new Tokenizer(new BufferedReader(new FileReader("myProgram.funny"))));
    }
}
