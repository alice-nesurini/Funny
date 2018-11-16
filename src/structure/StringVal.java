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

    public String getValue() {
        return string;
    }
}
