package structure;

import java.util.HashMap;
import java.util.Map;

public class Frame {
    private final Map<String, Val> map=new HashMap<>();

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
