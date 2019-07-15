package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public Parser(){}

    public void parse(Configuration configuration) throws Exception{
        try {
            Scanner scanner = new Scanner(new File("/Users/Andres/Desktop/myfile.txt"));
            while (scanner.hasNextLine()) {
                // String s = "\taddi\tr1, r0, 4";
                String str = scanner.nextLine().trim();
                if (str.startsWith("#") || str.startsWith(".") || str.length()==0)
                    continue; //Exit this iteration if line starts with # or .
                List<String> words = Arrays.asList(str.split("\\W+"));
                System.out.println(Arrays.toString(words.toArray()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}