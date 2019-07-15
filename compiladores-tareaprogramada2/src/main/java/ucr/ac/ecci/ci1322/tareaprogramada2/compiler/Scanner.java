package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import org.apache.commons.lang3.StringUtils;
import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Scanner {

    private int dataSize;
    private int lineCounter;

    public Scanner(){
        dataSize = 512;
        lineCounter = 0;
    }

    public boolean isFileCorrect(Configuration configuration)throws Exception{
        boolean correct = true;

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configuration.getSourceFilePath());
            scanner = new java.util.Scanner(inputStream, "UTF-8");
            String line;
            String message = "";
            int messageSize = 0;
            List<String> words;
            boolean data = false;
            boolean code = false;

            while(scanner.hasNextLine() && correct){
                lineCounter++;
                line = scanner.nextLine().trim();
                if (line.startsWith("#") || line.length()==0 || line.startsWith("main:"))
                    continue; //Exit this iteration if line starts with # or is empty or is main:

                if(line.startsWith(".data")){
                    data = true;
                    continue;
                }

                if(data && code){
                    System.out.println("(Error) Data segment already declared");
                    correct = false;
                    break;
                }

                if(line.startsWith(".code") && !data){
                    System.out.println("(Error) Data segment must be declared at least");
                    correct = false;
                    break;
                }

                if(line.startsWith(".code")){
                    data = false;
                    code = true;
                    continue;
                }

                if(line.contains("#")) //Drop comments from line
                    line = line.substring(0,line.indexOf("#")).trim();

                //Process data segment
                if(data){
                    if(line.contains("\"")){
                        //If the line has an ascii message splits the line in the message and the rest
                        message = line.substring(line.indexOf("\"")).trim();
                        line = line.substring(0,line.indexOf("\"")).trim();
                    }

                    //Splits the line between the whitespaces
                    words = Arrays.asList(line.split("\\s+"));
                    if(words.get(1).equals("byte")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"byte\" must be a number");
                            correct = false;
                            break;
                        }
                        dataSize -= 8;
                    } else if (words.get(1).equals("half")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"byte\" must be a number");
                            correct = false;
                            break;
                        }
                        dataSize -= 16;
                    } else if (words.get(1).equals("word")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"byte\" must be a number");
                            correct = false;
                            break;
                        }
                        dataSize -= 32;
                    } else if (words.get(1).equals(".ascii")){
                        if(!message.contains("\"")){
                            System.out.println("(Error) Line " + lineCounter + " The message for the ascii datatype must be enclosed in two \"");
                            correct = false;
                            break;
                        }
                        //Removes the ""
                        message = message.substring(1,message.length()-1);
                        //Ignores the reserved characters
                        for(int i = 0; i < message.length(); i++)
                            if(message.charAt(i) != '\\')
                                messageSize++;
                            else if(message.charAt(i) == '\\' && (i+1 == message.length())){
                                System.out.println("(Error) Line " + lineCounter + " Syntax error in the message '\\' cannot be alone");
                            }
                        dataSize -= messageSize;
                        messageSize = 0;
                    } else {
                        System.out.println("(Error) Line " + lineCounter + " Cannot resolve symbol " + words.get(1) + " in data segment");
                        correct = false;
                        break;
                    }
                }

                //Process the code segment
                if(code){

                }


                //words = Arrays.asList(line.split("\\W+"));
                //System.out.println(Arrays.toString(words.toArray()));

            }

        } catch (FileNotFoundException e) {
            System.err.println("Could not open file '" + configuration.getSourceFilePath() + "'.");
        } finally {
            if (inputStream != null)
                inputStream.close();
            scanner.close();
        }

        return correct;
    }
}