package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;
import ucr.ac.ecci.ci1322.tareaprogramada2.compiler.symboltable.SymbolTable;

/**
 * Class that compiles the source language into binary
 */
public class NewCompiler {

    private Scanner scanner;
    private Parser parser;
    private CodeGenerator codeGenerator;

    public NewCompiler(){
        scanner = new Scanner();
        parser = new Parser();
        codeGenerator = new CodeGenerator();
    }

    /**
     * Method that compiles the language, check for errors and generates a binary if there are no errors during compilation
     * @param configuration the file configuration of the user
     */
    public void compile(Configuration configuration) throws Exception{
        if(scanner.isFileCorrect(configuration)) {
            codeGenerator.generate(configuration, parser.parse(configuration));
            System.out.println("Compiled successfully");
        }
        else
            System.out.println("(Error) Errors occurred while compiling " + configuration.getSourceFileName());
    }
}