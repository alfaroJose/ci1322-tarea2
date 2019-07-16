package ucr.ac.ecci.ci1322.tareaprogramada2;

import ucr.ac.ecci.ci1322.tareaprogramada2.compiler.NewCompiler;

/**
 * Class that control the program
 */
public class Controller {

    private Configuration configuration;
    private NewCompiler newCompiler;

    public Controller(){
        configuration = new Configuration();
        newCompiler = new NewCompiler();
    }

    /**
     * Method that runs the compiler after the file configuration is asked
     */
    public void run() throws Exception{
        newCompiler.compile(configuration);
    }
}