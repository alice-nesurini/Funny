package structure;

public abstract class Val extends Expr {
    @Override
    public Val eval(Env env) {
        return this;
    }

    public StringVal checkString() throws InterpreterException {
        throw new InterpreterException("Not a string");
    }

    public NumVal checkNum() throws InterpreterException {
        throw new InterpreterException("Not a number");
    }

    public ClosureVal checkClosure() throws InterpreterException {
        throw new InterpreterException("Not a closure");
    }

    public BoolVal checkBool() throws InterpreterException {
        throw new InterpreterException("Not a bool");
    }

    public boolean less(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [less method]");
    }

    public boolean greater(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [greater method]");
    }

    public boolean lessEquals(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [lessEquals method]");
    }

    public boolean greaterEquals(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [greaterEquals method]");
    }

    public Val subtract(Val val) throws InterpreterException {
        throw new InterpreterException("Not numbers Can't apply substract (-=)");
    }

    public Val add(Val val) throws InterpreterException {
        throw new InterpreterException("Can't concatenate (+)");
    }

    public Val module(Val val) throws InterpreterException {
        throw new InterpreterException("Not numbers Can't apply module (%=)");
    }

    public Val star(Val val) throws InterpreterException {
        throw new InterpreterException("Not numbers Can't apply multiplication (*=)");
    }

    public Val divide(Val eval) throws InterpreterException {
        throw new InterpreterException("Not numbers can't apply division (/=)");
    }

    public Boolean comparison(Val val) throws InterpreterException {
        throw new InterpreterException("Can't apply comparison (==)");
    }

    public Boolean difference(Val val) throws InterpreterException {
        throw new InterpreterException("Can't apply difference (!=)");
    }

    public Val addAsString(Val val) {
        // implemented here to be able to
        // concatenate str + num or bool + str ...
        return new StringVal(this.toString() + val.toString());
    }
}
