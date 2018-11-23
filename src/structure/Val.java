package structure;

public abstract class Val extends Expr {
    @Override
    public Val eval(Env env) {
        return this;
    }

    public StringVal checkString() throws InterpreterException {
        //TODO: modify the exception
        throw new InterpreterException("Not a string");
    }

    public NumVal checkNum() throws InterpreterException {
        throw new InterpreterException("Not a number");
    }
}
