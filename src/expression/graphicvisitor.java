package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import org.antlr.v4.runtime.tree.ParseTree;

public class graphicvisitor extends ExprBaseVisitor<String> {
    private int nodeId = 0; // A counter to uniquely identify each node
    public StringBuilder dotGraphBuilder = new StringBuilder();
    // Helper method to create a new node and return its unique identifier
    private String createNode(String label) {
        String nodeIdString = "node" + nodeId++;
        dotGraphBuilder.append(nodeIdString).append(" [label=\"").append(label).append("\"];\n");
        return nodeIdString;
    }

    private void createEdge(String parentNodeId, String childNodeId) {
        dotGraphBuilder.append(parentNodeId).append(" -> ").append(childNodeId).append(";\n");
    }

    public String getDotGraph() {
        String dotGraph = "digraph AST {\n" + dotGraphBuilder.toString() + "}\n";
        dotGraphBuilder.setLength(0); // Clear the contents of the StringBuilder
        return dotGraph;
    }

    @Override
    public String visitProgram(ExprParser.ProgramContext ctx) {
        String programNodeId = createNode("Program");
        for (ParseTree child : ctx.children) {
            String childNodeId = visit(child);
            createEdge(programNodeId, childNodeId); // Add this line to create an edge between Program and its child
            System.out.println(programNodeId + " -> " + childNodeId + ";");
        }
        return programNodeId;
    }


    @Override
    public String visitDeclaration(ExprParser.DeclarationContext ctx) {
        String id = ctx.ID().getText();
        String type = ctx.INT_TYPE().getText();

        // Create a node for the declaration
        String declNodeId = createNode("Declaration: " + id );

        // Visit the first expression and create an edge from the declaration node to this expression node
        String exprNodeId = visit(ctx.expr(0)); // Assuming visit returns the node ID of the expression
        createEdge(declNodeId, exprNodeId);

        // Handling multiple expressions
        for (int i = 1; i < ctx.expr().size(); i++) {
            exprNodeId = visit(ctx.expr(i)); // Visit each expression and get its node ID
            createEdge(declNodeId, exprNodeId); // Create an edge from the declaration node to each expression node
        }

        return declNodeId; // Return the node ID of the declaration
    }
    @Override
    public String visitParenthesizedExpr(ExprParser.ParenthesizedExprContext ctx) {
        String nodeId = createNode("( )");
        String childNodeId = visit(ctx.expr()); // Assuming visit returns the node ID of the expression

        createEdge(nodeId, childNodeId); // Create an edge from the parenthesized expression to the child expression

        return nodeId; // Return the node ID of the parenthesized expression
    }





// Assume createNode and createEdge methods are defined elsewhere in your program


    @Override
    public String visitMultiplication(ExprParser.MultiplicationContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String multiplicationNodeId = createNode("*");
        createEdge(multiplicationNodeId, left);
        createEdge(multiplicationNodeId, right);
        return multiplicationNodeId;
    }

    @Override
    public String visitAddition(ExprParser.AdditionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        String additionNodeId = createNode("+");
        createEdge(additionNodeId, left);
        createEdge(additionNodeId, right);
        return additionNodeId;
    }

    @Override
    public String visitVariable(ExprParser.VariableContext ctx) {
        return createNode(ctx.ID().getText());
    }

    @Override
    public String visitSubtraction(ExprParser.SubtractionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        String subtractionNodeId = createNode("-");
        createEdge(subtractionNodeId, left);
        createEdge(subtractionNodeId, right);
        return subtractionNodeId;
    }

    @Override
    public String visitNumber(ExprParser.NumberContext ctx) {
        return createNode(ctx.getText());
    }

    @Override
    public String visitDivision(ExprParser.DivisionContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        String divisionNodeId = createNode("/");
        createEdge(divisionNodeId, left);
        createEdge(divisionNodeId, right);
        return divisionNodeId;
    }

    @Override
    public String visitPrintStatement(ExprParser.PrintStatementContext ctx) {
        String expression = visit(ctx.expr());
        String printNodeId = createNode("print");
        createEdge(printNodeId, expression);
        return printNodeId;
    }

    public String visitEquals(ExprParser.EqualsContext ctx){
        String left = visit(ctx.expr(0)); // Visit the first expression
        String right = visit(ctx.expr(1)); // Visit the second expression
        String equalsNodeId = createNode("=");
        createEdge(equalsNodeId, left);
        createEdge(equalsNodeId, right);
        return equalsNodeId;
    }

    public String visitLessThan(ExprParser.LessThanContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String lessThanNodeId = createNode("<");
        createEdge(lessThanNodeId, left);
        createEdge(lessThanNodeId, right);
        return lessThanNodeId;
    }

    public String visitLessThanOrEqual(ExprParser.LessThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String lessThanOrEqualNodeId = createNode("<=");
        createEdge(lessThanOrEqualNodeId, left);
        createEdge(lessThanOrEqualNodeId, right);
        return lessThanOrEqualNodeId;
    }

    public String visitGreaterThan(ExprParser.GreaterThanContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String greaterThanNodeId = createNode(">");
        createEdge(greaterThanNodeId, left);
        createEdge(greaterThanNodeId, right);
        return greaterThanNodeId;
    }

    public String visitGreaterThanOrEqual(ExprParser.GreaterThanOrEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String greaterThanOrEqualNodeId = createNode(">=");
        createEdge(greaterThanOrEqualNodeId, left);
        createEdge(greaterThanOrEqualNodeId, right);
        return greaterThanOrEqualNodeId;
    }

    public String visitEqual(ExprParser.EqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String equalNodeId = createNode("==");
        createEdge(equalNodeId, left);
        createEdge(equalNodeId, right);
        return equalNodeId;
    }

    public String visitNotEqual(ExprParser.NotEqualContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String notEqualNodeId = createNode("!=");
        createEdge(notEqualNodeId, left);
        createEdge(notEqualNodeId, right);
        return notEqualNodeId;
    }

    public String visitLogicalAnd(ExprParser.LogicalAndContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String logicalAndNodeId = createNode("&&");
        createEdge(logicalAndNodeId, left);
        createEdge(logicalAndNodeId, right);
        return logicalAndNodeId;
    }

    public String visitLogicalOr(ExprParser.LogicalOrContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String logicalOrNodeId = createNode("||");
        createEdge(logicalOrNodeId, left);
        createEdge(logicalOrNodeId, right);
        return logicalOrNodeId;
    }

    public String visitIncrement(ExprParser.IncrementContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String incrementNodeId = createNode("+=");
        createEdge(incrementNodeId, left);
        createEdge(incrementNodeId, right);
        return incrementNodeId;
    }

    public String visitDecrement(ExprParser.DecrementContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String decrementNodeId = createNode("-=");
        createEdge(decrementNodeId, left);
        createEdge(decrementNodeId, right);
        return decrementNodeId;
    }

    public String visitModulo(ExprParser.ModuloContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String moduloNodeId = createNode("%");
        createEdge(moduloNodeId, left);
        createEdge(moduloNodeId, right);
        return moduloNodeId;
    }

    @Override
    public String visitIfElseStatement(ExprParser.IfElseStatementContext ctx) {
        String condition = visit(ctx.expr(0));
        String ifNodeId = createNode("if");
        createEdge(ifNodeId, condition);

        String thenExpression = visit(ctx.expr(1));
        String thenNodeId = createNode("then");
        createEdge(ifNodeId, thenNodeId);
        createEdge(thenNodeId, thenExpression);

        if (ctx.expr().size() > 2) {
            String elseExpression = visit(ctx.expr(2));
            String elseNodeId = createNode("else");
            createEdge(ifNodeId, elseNodeId);
            createEdge(elseNodeId, elseExpression);
        }

        return ifNodeId;
    }

    @Override
    public String visitWhileStatement(ExprParser.WhileStatementContext ctx) {
        String condition = visit(ctx.stmt(0));
        String whileNodeId = createNode("while");
        createEdge(whileNodeId, condition);

        for (int i = 1; i < ctx.stmt().size(); i++) {
            String statement = visit(ctx.stmt(i));
            createEdge(whileNodeId, statement);
        }

        return whileNodeId;
    }


}