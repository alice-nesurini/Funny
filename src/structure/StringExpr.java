package structure;

public class StringExpr extends Expr{

    private final String string;

    public StringExpr(String string) {
        this.string = string;
    }

    @Override
    public <T> T getValue() {
        return null;
    }
}
