package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;
import ucr.ac.ecci.ci1322.tareaprogramada2.compiler.symboltable.SymbolTable;

public class NewCompiler {

    private SymbolTable symbolTable;
    private Scanner scanner;
    private Parser parser;
    private CodeGenerator codeGenerator;

    public NewCompiler(){
        symbolTable = new SymbolTable();
        scanner = new Scanner();
        parser = new Parser();
        codeGenerator = new CodeGenerator();
    }

    public void compile(Configuration configuration){
        if(scanner.isFileCorrect(configuration))
            parser.parse(configuration);
    }
}