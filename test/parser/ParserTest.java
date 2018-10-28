package parser;

import org.junit.jupiter.api.Test;
import structure.Expr;
import structure.ExprSequence;
import structure.NumExpr;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void testNumericSequence() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=2}"));
        Expr expr=new Parser(tokenizer).parse();
        assertEquals(1, ((ExprSequence)expr).size());

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{(myVar param id other) -> notExistent=2}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=-0.6; param=5;}"));
        parser=new Parser(tokAssignments);
        ExprSequence seq=(ExprSequence)parser.parse();
        assertEquals(2, (seq).size());
        seq.print();

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=5e-4; param=.5}"));
        parser=new Parser(tokNoEnd);
        seq=(ExprSequence)parser.parse();
        assertEquals(2, (seq).size());
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

    @Test
    public void testBooleanAssignment() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true}"));
        Expr expr=new Parser(tokenizer).parse();
        assertEquals(1, ((ExprSequence)expr).size());

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{(myVar param id other) -> notExistent=false}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true; param=false;}"));
        parser=new Parser(tokAssignments);
        ExprSequence seq=(ExprSequence)parser.parse();
        assertEquals(2, (seq).size());

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true;" +
                " param=false; id=true}"));
        parser=new Parser(tokNoEnd);
        seq=(ExprSequence)parser.parse();
        assertEquals(3, (seq).size());
        seq.print();
    }
}
