package ucr.ac.ecci.ci1322.tareaprogramada2;

import ucr.ac.ecci.ci1322.tareaprogramada2.compiler.NewCompiler;

public class Controller {

    private Configuration configuration;
    private NewCompiler newCompiler;

    public Controller(){
        configuration = new Configuration();
        newCompiler = new NewCompiler();
    }

    public void run(){
        newCompiler.compile(configuration);
    }
}