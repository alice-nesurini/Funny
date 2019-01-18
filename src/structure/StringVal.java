package structure;

public class StringVal extends Val {

    private final String string;

    public StringVal(String string) {
        this.string = string;
    }

    @Override
    public StringVal checkString() {
        return this;
    }

    @Override
    public Boolean comparison(Val val) throws InterpreterException {
        return this.string.equals(val.checkString().string);
    }

    @Override
    public Boolean difference(Val val) throws InterpreterException {
        return !this.string.equals(val.checkString().string);
    }

    @Override
    public String toString() {
        return string;
    }
}
