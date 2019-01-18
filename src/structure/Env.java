package structure;

public class Env {
    private final Frame frame;
    private final Env enclosing;

    public Env(Frame frame, Env enclosing) {
        // enclosing environment CAN be null
        if (frame == null) {
            throw new IllegalArgumentException("frame can't be null");
        }
        this.frame = frame;
        this.enclosing = enclosing;
    }

    public Val add(String id, Val val) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id can't be empty/null");
        }
        frame.add(id, val);
        return val;
    }

    public Val get(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id can't be null");
        }
        return frame.contains(id) ? frame.get(id) : enclosing.get(id);
    }
}
