package structure;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.*;

class EnvTest {

    @Test
    public void create0(){
        // empty frame, excpected exception
        assertThrows(IllegalArgumentException.class, ()->new Env(null, null));
    }

    @Test
    public void create1(){
        // all ok
        Env env=new Env(mock(Frame.class), null);
        assertNotNull(env);
    }

    @Test
    void add0() {
        // null id, val expected exception
        Env env=new Env(mock(Frame.class), null);
        assertThrows(IllegalArgumentException.class, ()->env.add(null, null));
    }

    @Test
    void add1() {
        // empty id expected exception
        Env env=new Env(mock(Frame.class), null);
        assertThrows(IllegalArgumentException.class, ()->env.add("", mock(Val.class)));
    }

    @Test
    void add2() {
        // all ok
        Env env=new Env(mock(Frame.class), null);
        Env envSpy=spy(env);
        envSpy.add("param", mock(Val.class));
        // verify problem
        // verify(envSpy, times(1)).frame.add(any(String.class), any(Val.class));
    }

    @Test
    void get0() {
        // null id, expected exception
        Env env=new Env(mock(Frame.class), null);
        env.add("id", mock(Val.class));
        assertThrows(IllegalArgumentException.class, ()->env.get(null));
    }

    @Test
    void get1() {
        // empty id, expected exception
        Env env=new Env(mock(Frame.class), null);
        env.add("id", mock(Val.class));
        assertThrows(IllegalArgumentException.class, ()->env.get(""));
    }

    @Test
    void get2() {
        // all ok
        Env env=new Env(mock(Frame.class), null);
        /*Val val=env.add("id", mock(Val.class));
        assertNotNull(val);
        val=env.get("id");
        assertNotNull(val);*/
    }
}