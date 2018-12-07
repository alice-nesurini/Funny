package structure;

public class BoolVal extends Val {
    private final Boolean value;

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
    //TODO: implement inverted logic
}
