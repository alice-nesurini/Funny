import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import structure.*;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpreterTest {
    @Test
    public void simplePrintTest() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\");print(\"Other Hello\")}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void multiPrintTest() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{->print(\"Hello\n\",\"Test\", \"java\");}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleNumAssign() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{param->param=2+7;print(\"param: \", param)}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleAssign() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{a->a=5;a=a-5+2;print(\"a: \", a)}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void majorClosure() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{fz _false _if ->" +
                "fz = {(t f) -> t};" +
                "println(fz(10, 4))" +
                "}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleWhile() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{n ->\n" +
                "n=0;\n" +
                "while n < 10 do\n" +
                "    println(\"n: \", n);\n" +
                "    n = n+1;\n" +
                "od"+
                "}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleWhileNot() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{n ->\n" +
                "n=0;\n" +
                "whilenot n > 10 do\n" +
                "    println(\"n: \", n);\n" +
                "    n = n+1;\n" +
                "od"+
                "}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void counterClosure() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{makeCounter myCounter yourCounter n ->\n" +
                "\n" +
                "    makeCounter = {(balance) ->\n" +
                "        {(amount) -> balance += amount}\n" +
                "    };\n" +
                "    \n" +
                "    myCounter = makeCounter(100);\n" +
                "    yourCounter = makeCounter(50);\n" +
                "    \n" +
                "    println(\"myCounter: \", myCounter(0));\n" +
                "    println(\"yourCounter: \", yourCounter(0));\n" +
                "    println(\"\");\n" +
                "    n = 0;\n"+
                "    while n < 10 do\n" +
                "        print(\"myCounter[\", n, \"]: \", myCounter(50), \"\n\");\n" +
                "        print(\"yourCounter[\", n, \"]: \", yourCounter(-10), \"\n\");\n" +
                "        n += 1;\n" +
                "        print(\"n:\", n);"+
                "    od"+
                "}"));
        // Problema con il -10
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void simpleMinusEquals() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{n ->\n" +
                "n=2;" +
                "n+=1;" +
                "print(n)" +
                "}"));
        Expr expr = new Parser(tokenizer).parse();
        expr.eval(null);
    }

    @Test
    public void fibonacciIteractive() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{fib ->\n" +
                "    fib = {(n) fib0 fib1 fib ->\n" +
                "        fib0 = 1;\n" +
                "        fib1 = 0;\n" +
                "        while n > 0 do\n" +
                "            fib = fib0 + fib1;\n" +
                "            fib0 = fib1;\n" +
                "            fib1 = fib;\n" +
                "            n -= 1\n" +
                "        od;\n" +
                "        fib1\n" +
                "    };\n" +
                "    \n" +
                "    println(fib(10000))\n" +
                "}"));
        Expr expr = new Parser(tokenizer).parse();
    }

    @Test
    void simpleIf() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{n ->\n" +
                "n=0;"+
                "if n<2 then " +
                " n=n+1;" +
                "else" +
                " println(\"none\");" +
                "fi;\n" +
                "println(n);\n" +
                "}"));
        Expr expr = new Parser(tokenizer).parse();
    }

    @Test
    void fibonacciRicorsive() throws TokenizerException, ParserException, IOException, InterpreterException {
        final Tokenizer tokenizer = new Tokenizer(new StringReader("{fib ->\n" +
                "    fib = {(n) ->\n" +
                "        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
                "    };\n" +
                "    \n" +
                "    println(fib(30))\n" +
                "}"));
        Expr expr = new Parser(tokenizer).parse();
    }

    @Test
    public void binaryAdderFunction() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader(("{binaryAdder ->" +
                "binaryAdder = {(x) -> {(y) -> x + y}};\n" +
                "println(binaryAdder(123)(234));" +
                "}")));
        Expr expr = new Parser(tokenizer).parse();
    }

    @Test
    public void setteDiOtto() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader(("{a sqr x ->\n" +
                "\n" +
                "\tsqr = {(x) -> x * x};\n" +
                "\tx = {(z) -> sqr};\n" +
                "\t\n" +
                "\tprintln(x(2)(3));\n" +
                "\n" +
                "    println(10 / 3);\n" +
                "    println(20 / 3);\n" +
                "    \n" +


                "\tprintln({(x)->{() -> x}}(4)());\n" +

                // expected not a closure
                "\t7(8);\n" +


                "\t\n" +
                "    a = 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024;\n" +
                "    println(a);\n" +
                "    println(1 / a);\n" +
                "    println(1 / a * a);\n" +
                "    println(1 / a * a == 1);\n" +
                "    \n" +
                "    println(3.27 % .7);\n" +
                "}")));
        assertThrows(InterpreterException.class, ()->new Parser(tokenizer).parse());
    }

    @Test
    public void emptyPrint() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader(("{ -> print()}")));
        Expr expr = new Parser(tokenizer).parse();
    }
}