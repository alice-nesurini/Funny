package structure;

public class PrintExpr extends Expr{

    private final ExprList sequence;

    public PrintExpr(ExprList sequence) {
        this.sequence = sequence;
    }

    @Override
    public Val eval(Env env){
        sequence.eval(env).forEach(e-> {
            try {
                System.out.print(e.eval(env).checkString().getValue());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        // TODO: what to return? nothing?
        return null;
    }
}
