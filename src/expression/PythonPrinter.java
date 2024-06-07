package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import org.antlr.v4.runtime.tree.ParseTree;

public class PythonPrinter extends ExprBaseVisitor<String> {
    @Override
    public String visitProgram(ExprParser.ProgramContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (ParseTree child : ctx.children) {
            sb.append(visit(child)).append("");
        }
        return sb.toString().trim();
    }

    @Override
    public String visitDeclaration(ExprParser.DeclarationContext ctx) {
        String id = ctx.ID().getText();
        String type = ctx.INT_TYPE().getText(); // Assuming INT_TYPE is the token for the type
        StringBuilder value = new StringBuilder();

        if (ctx.expr().size() > 0) {
            value = new StringBuilder(visit(ctx.expr(0))); // Assuming expr() returns the string representation of the first expression.
            for (int i = 1; i < ctx.expr().size(); i++) {
                value.append(" ").append(visit(ctx.expr(i))); // Concatenating the string representations of subsequent expressions.
            }
        }

        return id + " = " + value+"\n";
    }

    @Override
    public String visitParenthesizedExpr(ExprParser.ParenthesizedExprContext ctx) {
        // Visit the nested expression to get its Python syntax representation
        String exprSyntax = visit(ctx.expr());

        // Return the Python syntax for the parenthesized expression
        return "(" + exprSyntax + ")";
    }



    @Override
    public String visitMultiplication(ExprParser.MultiplicationContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " * " + right;
    }

    @Override
    public String visitAddition(ExprParser.AdditionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " + " + right;
    }

    @Override
    public String visitVariable(ExprParser.VariableContext ctx) {
        return ctx.ID().getText();
    }
    @Override
    public String visitSubtraction(ExprParser.SubtractionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " - " + right;
    }


    @Override
    public String visitNumber(ExprParser.NumberContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitDivision(ExprParser.DivisionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " / " + right;
    }

    @Override
    public String visitPrintStatement(ExprParser.PrintStatementContext ctx) {
        // Visit the expression to get the Python equivalent string
        String expression = visit(ctx.expr());
        // Return the Python print statement
        return "print(" + expression + ")\n";
    }

    public String visitEquals(ExprParser.EqualsContext ctx){
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " = " + right;
    }

    public String visitLessThan(ExprParser.LessThanContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " < " + right;
    }

    public String visitLessThanOrEqual(ExprParser.LessThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " <= " + right;
    }

    public String visitGreaterThan(ExprParser.GreaterThanContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " > " + right;
    }

    public String visitGreaterThanOrEqual(ExprParser.GreaterThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " >= " + right;
    }

    public String visitEqual(ExprParser.EqualContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " == " + right;
    }

    public String visitNotEqual(ExprParser.NotEqualContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " != " + right;
    }

    public String visitLogicalAnd(ExprParser.LogicalAndContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " && " + right;
    }

    public String visitLogicalOr(ExprParser.LogicalOrContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " || " + right;
    }

    public String visitIncrement(ExprParser.IncrementContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " += " + right;
    }

    public String visitDecrement(ExprParser.DecrementContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " -= " + right;
    }

    public String visitModulo(ExprParser.ModuloContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        return left + " % " + right;
    }


    @Override
    public String visitIfElseStatement(ExprParser.IfElseStatementContext ctx) {
        StringBuilder ifStatement = new StringBuilder();

        // Visit the condition expression
        String condition = visit(ctx.expr(0));
        ifStatement.append("if ").append(condition).append(":\n");

        // Visit the 'then' expression
        String thenExpression = visit(ctx.expr(1));
        // Add proper indentation for the 'then' expression
        ifStatement.append(indent(thenExpression)+"\n");

        // Check if there is an 'else' expression
        if (ctx.expr().size() > 2) {
            String elseExpression = visit(ctx.expr(2));
            // Add the 'else' keyword with proper indentation
            ifStatement.append("else:\n");
            // Add proper indentation for the 'else' expression
            ifStatement.append(indent(elseExpression)+"\n");
        }

        return ifStatement.toString();
    }

    @Override
    public String visitWhileStatement(ExprParser.WhileStatementContext ctx) {
        StringBuilder whileLoop = new StringBuilder();

        // Visit the condition expression
        String condition = visit(ctx.stmt(0));
        whileLoop.append("while ").append(condition).append(":\n");

        // Visit each statement in the 'while' loop body
        for (int i = 1; i < ctx.stmt().size(); i++) {
            String statement = visit(ctx.stmt(i));
            // Add proper indentation for each statement in the body
            whileLoop.append(indent(statement)+"\n");
        }

        return whileLoop.toString();
    }

    // Helper method to add indentation
    private String indent(String expression) {
        return "    " + expression.replace("\n", "\n    ");
    }



}
