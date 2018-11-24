package structure;

public class Env {
    private final Frame frame;
    private final Env enclosing;

    public Env(Frame frame, Env enclosing) {
        this.frame = frame;
        this.enclosing = enclosing;
    }

    public void add(String id, Val val){
        frame.add(id, val);
    }

    public Val get(String id){
        return frame.contains(id)?frame.get(id):enclosing.get(id);
    }
}
