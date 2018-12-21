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

    public Boolean and(BoolVal val){
        return this.toBool()&&val.toBool();
    }

    public Boolean or(BoolVal val){
        return this.toBool()||val.toBool();
    }

    @Override
    public Boolean comparison(Val val) throws InterpreterException {
        return this.value==val.checkBool().value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
