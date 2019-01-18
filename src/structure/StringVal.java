package structure;

public class StringVal extends Val {

    private final String string;

    public StringVal(String string) {
        this.string = string;
    }

    @Override
    public StringVal checkString(){
        return this;
    }

    // two string can be concatenated using '+'
    // so the Val plus method is overriden
    // TODO : add anche contrario in Val
    @Override
    public Val add(Val val) throws InterpreterException {
        return new StringVal(this.toString()+val.checkString().toString());
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public Boolean comparison(Val val) throws InterpreterException {
        return this.string.equals(val.checkString().string);
    }

    @Override
    public Boolean difference(Val val) throws InterpreterException {
        return !this.string.equals(val.checkString().string);
    }
}
