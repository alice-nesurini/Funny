package parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import structure.SeqExpr;
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
        SeqExpr expr=new Parser(tokenizer).parse();
        assertEquals(1, (expr).size());

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{(myVar param id other) -> notExistent=2}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=-0.6; param=5;}"));
        parser=new Parser(tokAssignments);
        SeqExpr seq=(SeqExpr)parser.parse();
        assertEquals(2, (seq).size());

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=5e-4; param=.5}"));
        parser=new Parser(tokNoEnd);
        seq=(SeqExpr)parser.parse();
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
        SeqExpr expr=new Parser(tokenizer).parse();
        assertEquals(1, (expr).size());

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{(myVar param id other) -> notExistent=false}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true; param=false;}"));
        parser=new Parser(tokAssignments);
        SeqExpr seq=(SeqExpr)parser.parse();
        assertEquals(2, (seq).size());

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true;" +
                " param=false; id=true}"));
        parser=new Parser(tokNoEnd);
        seq=(SeqExpr)parser.parse();
        assertEquals(3, (seq).size());
    }

    @Test
    public void testLogicalOr() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true||false||false}"));
        SeqExpr expr = new Parser(tokenizer).parse();;
        assertEquals(1, (expr).size());
    }

    //@Disabled
    @Test
    public void testComparison() throws TokenizerException, ParserException, IOException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{(myVar param id other) -> myVar=true==false;" +
                "param=5<1;"+
                "other=5+4*8/2;"+
                "id=!true;"+
                "id=\"Mia Stringa\";"+
                "if(id==true) then other=5; fi;"+
                "if id==true then other=5; fi;"+
                "while id==true do other=5; od;"+
                "while (id==true) do other=5; od;"+
                "print(\"Hello\");"+
                "print(\"Super\", \"Hello\")"+
                "}"));
        SeqExpr expr = new Parser(tokenizer).parse();
        expr.getExprs();
    }
}
