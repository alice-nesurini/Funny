package structure;

public class PrintExpr extends Expr{

    private final ExprList sequence;

    public PrintExpr(ExprList sequence) {
        this.sequence = sequence;
    }

    @Override
    public Val eval(Env env){
        sequence.eval(env).forEach(e-> {
            System.out.println("\tSEQ type : "+e);
            try {
                String id=e.eval(env).checkString().getValue();
                if(env.contains(id)){
                    System.out.print(env.get(e.eval(env).checkString().getValue()).checkNum().getValue());
                }
                else{
                    System.out.print(id);
                }
                //System.out.print(e.eval(env).checkNum().getValue());
            } catch (InterpreterException ie) {
                ie.printStackTrace();
            }
        });
        // TODO: what to return? nothing?
        return null;
    }
}
