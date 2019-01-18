package structure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FrameTest{

    @Test
    public void create0(){
        //create with null parameter, expected exception
        assertThrows(IllegalArgumentException.class, ()->new Frame(null, null, null));
    }

    @Test
    public void create1(){
        //create with null parameter, expected exception
        assertThrows(IllegalArgumentException.class, ()->new Frame(new ArrayList<>(), null, null));
    }

    @Test
    public void create2(){
        // param and argValue should have same dimension, expected IllegalArgument
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class));
        assertThrows(IllegalArgumentException.class, ()->new Frame(params, new ArrayList<>(), argVals));
    }

    @Test
    public void create3(){
        // all ok
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        assertNotNull(frame);
    }

    @Test
    void add0() {
        // adding null values, expected Exception
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        assertThrows(IllegalArgumentException.class, ()->frame.add(null, null));
    }

    @Test
    void add1() {
        // all ok
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        Frame frameSpy=spy(frame);
        frameSpy.add("param", mock(Val.class));
        // verify(frameSpy).map.put(any(String.class), any(Val.class));
    }

    @Test
    void get2() {
        // id not in the list (should never happen...)
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        Val val=frame.get("notInTheList");
        assertTrue(val instanceof Val);
        assertTrue(val instanceof NilVal);
    }

    @Test
    void get3() {
        // all ok
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        Val val=frame.get("var");
        assertTrue(val instanceof Val);
    }

    @Test
    void contains0() {
        // contains empty id, return false, delegate to map contains
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        assertFalse(frame.contains(null));
    }

    @Test
    void contains1() {
        // contains id not in list, return false
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        assertFalse(frame.contains("notInTheList"));
    }

    @Test
    void contains2() {
        // all ok
        List<String> params=Arrays.asList("var", "id");
        List<Val> argVals=Arrays.asList(mock(Val.class), mock(Val.class));
        Frame frame=new Frame(params, new ArrayList<>(), argVals);
        assertTrue(frame.contains("var"));
    }
}