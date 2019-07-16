package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;

import java.io.File;
import java.io.FileWriter;

public class CodeGenerator {

    //Output code file
    public void generate(Configuration configuration, StringBuilder code){
        try {
            String str = code.toString();
            File newFile = new File(configuration.getTargetFilePath() + configuration.getTargetFileName());

            FileWriter fw = new FileWriter(newFile);
            fw.write(str);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
