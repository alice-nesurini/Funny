package structure;

public class Env {
    private final Frame frame;
    private final Env enclosing;

    public Env(Env enclosing) {
        this.frame = new Frame();
        this.enclosing = enclosing;
    }

    public void add(String id, Val val){
        frame.add(id, val);
    }

    public boolean contains(String id){
        if(enclosing == null){
            return frame.contains(id);
        }
        return frame.contains(id) || enclosing.frame.contains(id);
    }

    public Val get(String id){
        return frame.contains(id)?frame.get(id):enclosing.frame.get(id);
    }
}
