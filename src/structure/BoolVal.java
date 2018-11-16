package structure;

public class BoolVal extends Val {
    private final Boolean value;

    public BoolVal(Boolean value) {
        this.value=value;
    }

    public Boolean getValue(){
        return this.value;
    }
}
