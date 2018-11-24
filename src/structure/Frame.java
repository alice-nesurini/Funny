package structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame {
    private final Map<String, Val> map=new HashMap<>();

    public Frame(List<String> params, List<String> locals, List<Val> argVals) {
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
        map.put(id, val);
    }

    public Val get(String id){
        return map.get(id);
    }

    public boolean contains(String id){
        return map.containsKey(id);
    }
}
