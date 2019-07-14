package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

public class NewCompiler {

    private Scanner scanner;
    private Parser parser;
    private CodeGenerator codeGenerator;

    public NewCompiler(){
        scanner = new Scanner();
        parser = new Parser();
        codeGenerator = new CodeGenerator();
    }


}