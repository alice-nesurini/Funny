package structure;

public class NilVal extends Val {

    // NilVal is singleton

    private static NilVal nil;

    private NilVal() {
    }

    public static NilVal instance() {
        if (nil == null) {
            nil = new NilVal();
        }
        return nil;
    }

    @Override
    public String toString() {
        return "Nil";
    }
}
