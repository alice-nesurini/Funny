package structure;

public abstract class Val extends Expr {
    @Override
    public Val eval(Env env) {
        return this;
    }

    public StringVal checkString() throws Exception {
        //TODO: modify the exception
        throw new Exception("Not a string");
    }
}
