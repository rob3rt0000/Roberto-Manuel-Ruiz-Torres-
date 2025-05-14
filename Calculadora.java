import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Calculadora Científica (escribe 'salir' para terminar)");
        while (true) {
            System.out.print("\nIngrese expresión: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("salir")) {
                break;
            }

            try {
                CharStream stream = CharStreams.fromString(input);
                calcLexer lexer = new calcLexer(stream);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                calcParser parser = new calcParser(tokens);
                
                // Configurar manejo de errores
                parser.removeErrorListeners();
                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
                        throw new RuntimeException("Error en línea " + line + ":" + charPositionInLine + " - " + msg);
                    }
                });
                
                ParseTree tree = parser.prog();
                EvalVisitor evaluator = new EvalVisitor();
                Double result = evaluator.visit(tree);
                
                System.out.println("\nÁrbol sintáctico:\n" + tree.toStringTree(parser));
                System.out.println("\nResultado: " + result);
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
