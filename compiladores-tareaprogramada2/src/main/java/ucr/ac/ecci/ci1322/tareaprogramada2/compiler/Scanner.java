package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import org.apache.commons.lang3.StringUtils;
import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;
import ucr.ac.ecci.ci1322.tareaprogramada2.compiler.symboltable.SymbolTable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Scanner {

    private int dataSize;
    private int lineCounter;
    private SymbolTable symbolTable;

    public Scanner(){
        dataSize = 512;
        lineCounter = 0;
        symbolTable = new SymbolTable();
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
                        //Splits the line between the whitespaces
                        words = Arrays.asList(line.split("\\s+"));
                        if(words.size() != 2){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                    } else {
                        //Splits the line between the whitespaces
                        words = Arrays.asList(line.split("\\s+"));
                        if(words.size() != 3){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                    }

                    //Add the names of the data segment variables to the symbol table
                    if(!symbolTable.findSymbol(words.get(0)))
                        symbolTable.addSymbol(words.get(0), "data");

                    if(words.get(1).equals("byte")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"byte\" must be a number");
                            correct = false;
                            break;
                        }
                        dataSize -= 8;
                    } else if (words.get(1).equals("half")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"half\" must be a number");
                            correct = false;
                            break;
                        }
                        dataSize -= 16;
                    } else if (words.get(1).equals("word")) {
                        if (!StringUtils.isNumeric(words.get(2))) {
                            System.out.println("(Error) Line " + lineCounter + " The value of the data type \"word\" must be a number");
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
                    if(line.contains(":")){
                        symbolTable.addSymbol(line.substring(0,line.indexOf(":")), "code");
                        continue;
                    }
                    words = Arrays.asList(line.split("\\W+"));
                    System.out.println(Arrays.toString(words.toArray()));
                    if(line.contains("syscall")){
                        if(words.size() != 1){
                            System.out.println("(Error) Line " + lineCounter + " syscall must be alone");
                            correct = false;
                            break;
                        }
                    }
                    else if(line.contains("ret")){
                        if(words.size() != 1){
                            System.out.println("(Error) Line " + lineCounter + " ret must be alone");
                            correct = false;
                            break;
                        }
                    }
                    else if(words.contains("push") || words.contains("pop") || words.contains("not")){
                        if(words.size() != 2){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                        if(!words.get(1).matches("^r(?:[0-9]|[12][0-9]|3[01])$")){
                            System.out.println("(Error) Line " + lineCounter + " registers goes from r0 to r31");
                            correct = false;
                            break;
                        }
                        //Adds registers to symbol table
                        if(words.get(1).matches("^r(?:[0-9]|[12][0-9]|3[01])$")){
                            symbolTable.addSymbol(words.get(1), "code");
                        }

                    }
                    else if(words.contains("add") || words.contains("sub") || words.contains("mul")
                            || words.contains("div") || words.contains("mod") || words.contains("and")
                            || words.contains("or") || words.contains("xor") || words.contains("sal")
                            || words.contains("sar") || words.contains("sll") || words.contains("slr")
                            || words.contains("scl") || words.contains("scr")){
                        if(words.size() != 4){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                        if(!words.get(1).matches("^r(?:[0-9]|[12][0-9]|3[01])$") || !words.get(2).matches("^r(?:[0-9]|[12][0-9]|3[01])$")
                            || !words.get(3).matches("^r(?:[0-9]|[12][0-9]|3[01])$")){
                            System.out.println("(Error) Line " + lineCounter + " registers goes from r0 to r31");
                            correct = false;
                            break;
                        }

                    }
                    else if(words.contains("call") || words.contains("jmp")){
                        if(words.size() != 2){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                        if( !symbolTable.findSymbol(words.get(1)) && !StringUtils.isNumeric(words.get(1))){
                            System.out.println("(Error) Line " + lineCounter + " Inm must be numeric or a variable in data segment");
                            correct = false;
                            break;
                        }
                    }
                    else if(words.contains("addi") || words.contains("subi") || words.contains("muli")
                            || words.contains("divi") || words.contains("modi") || words.contains("andi")
                            || words.contains("ori") || words.contains("xori") || words.contains("sali")
                            || words.contains("sari") || words.contains("slli") || words.contains("slri")
                            || words.contains("scli") || words.contains("scri") || words.contains("je")
                            || words.contains("jne") || words.contains("jgs") || words.contains("jgu")
                            || words.contains("jges") || words.contains("jgeu") || words.contains("jls")
                            || words.contains("jlu") || words.contains("jles") || words.contains("jleu")){
                        if(words.size() != 4){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                        if( !symbolTable.findSymbol(words.get(3)) && !StringUtils.isNumeric(words.get(3)) && !words.get(3).matches("^r(?:[0-9]|[12][0-9]|3[01])$")){
                            System.out.println("(Error) Line " + lineCounter + " Inm must be numeric or a variable in data segment");
                            correct = false;
                            break;
                        }
                    }
                    else if(words.contains("noti") || words.contains("lsb") || words.contains("lub")
                            || words.contains("lsh") || words.contains("luh") || words.contains("lsw")
                            || words.contains("luw") || words.contains("sb") || words.contains("sh")
                            || words.contains("sw")){
                        if(words.size() != 3 && words.size() != 4){
                            System.out.println("(Error) Line " + lineCounter + " instruction have incorrect arguments");
                            correct = false;
                            break;
                        }
                        if( !symbolTable.findSymbol(words.get(3)) && !StringUtils.isNumeric(words.get(2)) && !words.get(3).matches("^r(?:[0-9]|[12][0-9]|3[01])$")){
                            System.out.println("(Error) Line " + lineCounter + " Inm must be numeric or a variable in data segment");
                            correct = false;
                            break;
                        }
                    }
                    else {
                        System.out.println("(Error) Line " + lineCounter + " Cannot resolve symbol " + words.get(1) + " in code segment");
                        correct = false;
                        break;
                    }
                }
            }

            if(!data && !code){
                System.out.println("(Error) No data or code segment present");
                correct = false;
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