import java.util.*;

public class EvalVisitor extends calcBaseVisitor<Double> {
    @Override
    public Double visitProg(calcParser.ProgContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitNumeroExpr(calcParser.NumeroExprContext ctx) {
        return Double.parseDouble(ctx.NUMERO().getText());
    }

    @Override
    public Double visitPiExpr(calcParser.PiExprContext ctx) {
        return Math.PI;
    }

    @Override
    public Double visitEExpr(calcParser.EExprContext ctx) {
        return Math.E;
    }

    @Override
    public Double visitNegExpr(calcParser.NegExprContext ctx) {
        return -visit(ctx.expr());
    }

    @Override
    public Double visitPowExpr(calcParser.PowExprContext ctx) {
        return Math.pow(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Double visitMulDivExpr(calcParser.MulDivExprContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));
        return ctx.op.getText().equals("*") ? left * right : left / right;
    }

    @Override
    public Double visitAddSubExpr(calcParser.AddSubExprContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));
        return ctx.op.getText().equals("+") ? left + right : left - right;
    }

    @Override
    public Double visitFuncionCall(calcParser.FuncionCallContext ctx) {
        String funcName = ctx.FUNCION().getText();
        List<calcParser.ExprContext> args = ctx.expr();
        
        switch (funcName) {
            case "sin": return Math.sin(visit(args.get(0)));
            case "cos": return Math.cos(visit(args.get(0)));
            case "tan": return Math.tan(visit(args.get(0)));
            case "log": return Math.log10(visit(args.get(0)));
            case "ln": return Math.log(visit(args.get(0)));
            case "sqrt": return Math.sqrt(visit(args.get(0)));
            case "abs": return Math.abs(visit(args.get(0)));
            case "exp": return Math.exp(visit(args.get(0)));
            case "floor": return Math.floor(visit(args.get(0)));
            case "ceil": return Math.ceil(visit(args.get(0)));
            case "round": return (double) Math.round(visit(args.get(0)));
            case "min": return Math.min(visit(args.get(0)), visit(args.get(1)));
            case "max": return Math.max(visit(args.get(0)), visit(args.get(1)));
            default: throw new RuntimeException("Función no implementada: " + funcName);
        }
    }

    @Override
    public Double visitParenExpr(calcParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }
}
