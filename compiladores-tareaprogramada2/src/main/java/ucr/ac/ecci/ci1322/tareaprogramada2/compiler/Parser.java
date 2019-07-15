package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import org.apache.commons.lang3.StringUtils;
import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Parser {

    public Parser(){}

    public void parse(Configuration configuration) throws Exception{
        try {
            Scanner scanner = new Scanner(new File(configuration.getSourceFilePath()));
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine().trim();
                if (str.startsWith("#") || str.startsWith(".") || str.length()==0)
                    continue; //Exit this iteration if line starts with # or .
                if(str.contains("#")) //Drop comments from line
                    str = str.substring(0,str.indexOf("#"));
                List<String> words = Arrays.asList(str.split("\\W+"));
                System.out.println(Arrays.toString(words.toArray()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Binary conversion and padding
        String mystr = "12";
        int myint = Integer.parseInt(mystr);
        String newstr = StringUtils.leftPad(Integer.toBinaryString(myint), 16, '0');
        System.out.println(newstr);
        System.out.println(newstr.length());
    }
}