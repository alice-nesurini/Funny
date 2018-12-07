package structure;

public class BoolVal extends Val {
    private Boolean value;

    public BoolVal(Boolean value) {
        this.value=value;
    }

    @Override
    public BoolVal checkBool(){
        return this;
    }

    public boolean toBool(){
        return value;
    }

    public Val negate() {
        value=!value;
        return this;
    }
    //TODO: implement inverted logic
}
