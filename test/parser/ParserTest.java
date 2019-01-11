package parser;

import org.junit.jupiter.api.Test;
import structure.BoolVal;
import structure.Expr;
import structure.InterpreterException;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void testNumericSequence() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{myVar param id other -> myVar=2}"));
        Expr expr=new Parser(tokenizer).parse();

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{myVar param id other -> notExistent=2}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{myVar param id other-> myVar=-0.6; param=5;}"));
        parser=new Parser(tokAssignments);

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{myVar param id other -> myVar=5e-4; param=.5}"));
        parser=new Parser(tokNoEnd);
    }

    @Test
    public void testOptLocal() throws IOException, TokenizerException, ParserException, InterpreterException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{myVar param id ->}"));
        new Parser(tokenizer).parse();
    }

    @Test
    public void testBooleanAssignment() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{myVar param id other -> myVar=true}"));
        Expr expr=new Parser(tokenizer).parse();

        final Tokenizer tokenizerThrows=new Tokenizer(new StringReader("{myVar param id other -> notExistent=false}"));
        Parser parser=new Parser(tokenizerThrows);
        assertThrows(ParserException.class, parser::parse);

        final Tokenizer tokAssignments=new Tokenizer(new StringReader("{myVar param id other -> myVar=true; param=false;}"));
        parser=new Parser(tokAssignments);

        final Tokenizer tokNoEnd=new Tokenizer(new StringReader("{myVar param id other -> myVar=true;" +
                " param=false; id=true}"));
        parser=new Parser(tokNoEnd);
    }

    @Test
    public void testLogicalOr() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{myVar param id other -> myVar=true||false||false}"));
        Expr expr = new Parser(tokenizer).parse();
        assertEquals(new BoolVal(true).toString(), expr.eval(null).toString());
    }

    @Test
    public void testComparison() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{myVar param id other -> myVar=true==false;" +
                "param=5<1;"+
                "other=5+4*8/2;"+
                "id=!true;"+
                "if(id==true) then other=5; fi;"+
                "if id==true then other=5; fi;"+
                "while id==true do other=5; od;"+
                "while (id==true) do other=5; od;"+
                "print(\"Hello\");"+
                "print(\"Super\", \"Hello\")"+
                "}"));
        Expr expr = new Parser(tokenizer).parse();
    }
}
