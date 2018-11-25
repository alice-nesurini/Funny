package structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame {
    protected final Map<String, Val> map=new HashMap<>();

    public Frame(List<String> params, List<String> locals, List<Val> argVals) {
        if(params==null || locals==null || argVals==null){
            throw new IllegalArgumentException("the params/locals/argVals should be of type List");
        }
        if(params.size()!=argVals.size()){
            throw new IllegalArgumentException("params size doesn't much arg values");
        }
        // I parametri vengono inizializzati coi valori degli argomenti,
        // le variabili locali vengono inizializzate a nil.
        for(int i=0; i<params.size(); i++){
            map.put(params.get(i), argVals.get(i));
        }
        for(int i=0; i<locals.size(); i++){
            map.put(locals.get(i), NilVal.instance());
        }
    }

    public void add(String id, Val val){
        if(id==null || val==null){
            throw new IllegalArgumentException("Frame add, id and val should not be null");
        }
        map.put(id, val);
    }

    public Val get(String id){
        if(id==null || id.isEmpty()){
            throw new IllegalArgumentException("id can't be null/empty");
        }
        Val val=map.get(id);
        return val==null?NilVal.instance():val;
    }

    public boolean contains(String id){
        return map.containsKey(id);
    }
}
