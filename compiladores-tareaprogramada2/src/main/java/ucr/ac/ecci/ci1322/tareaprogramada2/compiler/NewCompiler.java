package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;

public class NewCompiler {

    private Scanner scanner;
    private Parser parser;

    public NewCompiler(){
        scanner = new Scanner();
        parser = new Parser();
    }

    public void compile(Configuration configuration) throws Exception{
        if(scanner.isFileCorrect(configuration))
            //parser.parse(configuration);
            System.out.println("[INFO] Compiled successfully");
        else
            System.out.println("(Error) Errors occurred while compiling " + configuration.getSourceFileName());
    }
}