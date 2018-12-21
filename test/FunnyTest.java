import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parser.ParserException;
import structure.InterpreterException;
import structure.Launcher;
import tokenizer.Tokenizer;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.io.StringReader;

public class FunnyTest {

    @Test
    public void sqrtTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{average sqr abs sqrt x ->\n" +
                "    average = {(x y) -> (x + y) / 2};\n" +
                "    sqr = {(x) -> x * x};\n" +
                "    abs = {(x) -> if x >= 0 then x else -x fi};\n" +
                "    sqrt = {(x) tolerance isGoodEnough improve sqrtIter ->\n" +
                "        tolerance = 1e-30;\n" +
                "\n" +
                "        isGoodEnough = {(guess) -> abs(sqr(guess) - x) < tolerance};\n" +
                "        improve = {(guess) -> average(guess, x / guess)};\n" +
                "        sqrtIter = {(guess) ->\n" +
                "            if isGoodEnough(guess) then guess else sqrtIter(improve(guess)) fi\n" +
                "        };\n" +
                "        sqrtIter(1)\n" +
                "    };\n" +
                "\n" +
                "    x = 16;\n" +
                "    println(\"sqrt(\", x, \"): \", sqrt(x));\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void stringTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ ->\n" +
                "\tprint(\"Hello, world!\n\");\n" +
                "\tprint(\"Hi!\", \"\n\");\n" +
                "\tprintln(\"你好\");\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    // the while true test...
    @Disabled
    @Test
    public void trueFalseUnderscoreTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{_true _false _if ->\n" +
                "    _true = {(t f) -> t};\n" +
                "    _false = {(t f) -> f};\n" +
                "    _if = {(c t e) -> c(t, e)()};\n" +
                "    \n" +
                "    println(_if(_true, {-> while true do {} od}, {-> \"False\"}))\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void coin() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{coin change ->\n" +
                "    coin = {(index) ->\n" +
                "        if index == 0\n" +
                "        then 500\n" +
                "        else if index == 1\n" +
                "            then 200\n" +
                "            else if index == 2\n" +
                "                then 100\n" +
                "                else if index == 3\n" +
                "                    then 50\n" +
                "                    else if index == 4\n" +
                "                        then 20\n" +
                "                        else if index == 5\n" +
                "                            then 10\n" +
                "                            else if index == 6\n" +
                "                                then 5\n" +
                "                                fi\n" +
                "                            fi\n" +
                "                        fi\n" +
                "                    fi\n" +
                "                fi\n" +
                "            fi\n" +
                "        fi\n" +
                "    };\n" +
                "    \n" +
                "    change = {(amount index) ->\n" +
                "        if amount == 0 then 1\n" +
                "        else if amount < 0 then 0\n" +
                "             else if index >= 7 then 0\n" +
                "                  else change(amount, index + 1) + change(amount - coin(index), index)\n" +
                "                  fi\n" +
                "             fi\n" +
                "        fi\n" +
                "    };\n" +
                "    \n" +
                "    println(change(50, 0))\n" +
                "}\n"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void myCounterTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{makeCounter myCounter yourCounter n ->\n" +
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
                "    println();\n" +
                "    \n" +
                "    n = 0;\n" +
                "    while n < 10 do\n" +
                "        println(\"myCounter[\", n, \"]: \", myCounter(50));\n" +
                "        println(\"yourCounter[\", n, \"]: \", yourCounter(-10));\n" +
                "        println();\n" +
                "        n += 1\n" +
                "    od\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void isEvenTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{isOdd isEven ->\n" +
                "\tisOdd = {(n) -> if n == 0 then false else isEven(n - 1) fi};\n" +
                "\tisEven = {(n) -> if n == 0 then true else isOdd(n - 1) fi};\n" +
                "\n" +
                "\tprintln(isEven(1000));\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void emptyTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{-> println({->} + \"a\")}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void setteDiOttoTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{a sqr x ->\n" +
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
                "\t//7(8);\n" +
                "\t\n" +
                "    a = 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024;\n" +
                "    println(a);\n" +
                "    println(1 / a);\n" +
                "    println(1 / a * a);\n" +
                "    println(1 / a * a == 1);\n" +
                "    \n" +
                "    println(3.27 % .7);\n" +
                "}"));
        Launcher.launch(tokenizer);
    }
    @Test
    public void fibRicTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{fib ->\n" +
                "    fib = {(n) ->\n" +
                "        if n < 2 then n else fib(n - 1) + fib(n - 2) fi\n" +
                "    };\n" +
                "    \n" +
                "    println(fib(20))\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void fibIterTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{fib ->\n" +
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
        Launcher.launch(tokenizer);
    }

    @Test
    public void hanoiTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{hanoi ->\n" +
                "    hanoi = {(n from to via) ->\n" +
                "        if n > 0 then\n" +
                "            hanoi(n - 1, from, via, to);\n" +
                "            println(from, \" -> \", to);\n" +
                "            hanoi(n - 1, via, to, from)\n" +
                "        fi\n" +
                "    };\n" +
                "    hanoi(10, \"left\", \"right\", \"center\")\n" +
                "}"));
        Launcher.launch(tokenizer);
    }

    @Test
    public void treeTest() throws TokenizerException, ParserException, InterpreterException, IOException {
        final Tokenizer tokenizer=new Tokenizer(new StringReader("{ pair head tail tree ->\n" +
                "    pair = {(h t) -> {(p) -> p(h, t)}};\n" +
                "    head = {(p) -> p({(h t) -> h})};\n" +
                "    tail = {(p) -> p({(h t) -> t})};\n" +
                "    \n" +
                "    tree = pair(1, pair(pair(\"two\", 42), pair(true, nil)));\n" +
                "    println(head(head(tail(tree))));\n" +
                "}"));
        Launcher.launch(tokenizer);
    }
}
