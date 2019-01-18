package parser;

import java.util.List;

public class Scope {

    private final List<String> ids;
    private final Scope enclosing;

    public Scope(List<String> ids, Scope enclosing) {
        this.ids = ids;
        this.enclosing = enclosing;
    }

    public boolean contains(String id) {
        if (enclosing != null) {
            return ids.contains(id) || enclosing.contains(id);
        }
        return ids.contains(id);
    }
}
