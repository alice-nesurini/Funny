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
    //TODO: add the method to override
    public Val plus(Val val) throws InterpreterException {
        return new StringVal(this.toString()+val.checkString().toString());
    }

    @Override
    public String toString() {
        return string;
    }
}
