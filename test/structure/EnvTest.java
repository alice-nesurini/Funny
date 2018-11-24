package structure;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EnvTest {

    private Env env=new Env(new Frame(funExpr.getParams(), funExpr.getLocals(), argVals), null);

    @Test
    void add() throws InterpreterException {
        BigDecimal b3=new BigDecimal(3);
        BigDecimal b4=new BigDecimal(4);
        BigDecimal b5=new BigDecimal(5);
        env.add("param", new NumVal(b3));
        env.add("myVar", new NumVal(b4));
        env.add("other", new NumVal(b5));

        assertEquals(b3, (env.get("param").checkNum().getValue()));
        assertEquals(b4, (env.get("myVar").checkNum().getValue()));
        assertEquals(b5, (env.get("other").checkNum().getValue()));
    }
}