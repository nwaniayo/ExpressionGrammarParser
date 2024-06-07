package app;

import expression.*;
import antlr.ExprLexer;
import antlr.ExprParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.IOException;

public class ExpressionApp {
    public static void main(String[] args){
        if(args.length !=1){
            System.err.print("Usage: file name");
        }
        else {
            String fileName =args[0];
            ExprParser parser = getParser(fileName);

            //tell antlr to build a parse tree
            //parse from the start symbol 'prog'
            ParseTree antlrAST = parser.prog();
            PythonPrinter visitor = new PythonPrinter();
            System.out.println("Python syntax:\n");
            System.out.println(visitor.visit(antlrAST)+'\n');
            String pythonCode = visitor.visit(antlrAST);
            // Specify the path where you want to save the .py file
            String outputPath = "output5.py";

            // Write the Python code to the file
            try (FileWriter fileWriter = new FileWriter(outputPath)) {
                fileWriter.write(pythonCode);
                System.out.println("Python code has been written to " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("AST graph syntax: \n");
            graphicvisitor dotVisitor = new graphicvisitor();
            dotVisitor.visit(antlrAST);
            String dotGraph = dotVisitor.getDotGraph() ;



            // Specify the path where you want to save the DOT file
            String dotPath = "output.dot";

            // Write the DOT output to the file
            try (FileWriter fileWriter = new FileWriter(dotPath)) {
                fileWriter.write(dotGraph);
                System.out.println("DOT file has been written to " + dotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Generate the visual representation using Graphviz
            try {
                ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotPath, "-o", "ast.png");
                pb.inheritIO();
                Process p = pb.start();
                p.waitFor();
                System.out.println("AST image has been generated as ast.png");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }





        }
    }


    private static ExprParser getParser(String filename){
        ExprParser parser =null;
        try {
            CharStream input = CharStreams.fromFileName(filename);
            ExprLexer lexer = new ExprLexer(input);
            CommonTokenStream tokens= new CommonTokenStream(lexer);
            parser= new ExprParser(tokens);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser;
    }
}
