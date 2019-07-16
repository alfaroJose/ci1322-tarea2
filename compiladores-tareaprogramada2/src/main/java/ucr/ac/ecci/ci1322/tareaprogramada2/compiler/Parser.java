package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import org.apache.commons.lang3.StringUtils;
import ucr.ac.ecci.ci1322.tareaprogramada2.Configuration;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that parses the code into a binary string
 */
public class Parser {

    private ConversionTables tables;
    private StringBuilder resultString;

    public Parser(){
        tables = new ConversionTables();
        resultString = new StringBuilder();
    }

    /**
     * Method that parses the source language file into a binary representation based on their equivalent
     * in the conversion tables
     * @param configuration the file configuration selected by the user
     * @return StringBuilder with the representation in binary of the source language file
     */
    public StringBuilder parse(Configuration configuration) throws Exception{
        try {
            Scanner scanner = new Scanner(new File(configuration.getSourceFilePath()));

            while (scanner.hasNextLine()) {

                //Remove beginning whitespace
                String str = scanner.nextLine().trim();

                //When we arrive at code segment, apply data segment bit padding to make 512B
                if(str.equals(".code")){
                    int padSize = 512;
                    if(!tables.getData().isEmpty()){
                        for(int val : tables.getData().values())
                            padSize -= val;
                    }
                    resultString.append(StringUtils.rightPad("", padSize*8, '0'));
                    continue;
                }

                //Ignore lines irrelevant to parser
                if (str.startsWith("#") || str.startsWith(".") || str.length()==0)
                    continue;

                //Drop comments from line
                if(str.contains("#"))
                    str = str.substring(0,str.indexOf("#"));

                //Find tags and add to map for processing in memory operations
                Pattern p = Pattern.compile("^(\\w+):$");
                Matcher m = p.matcher(str);
                if (m.find()) {
                    if(!m.group(1).equals("main"))
                        tables.addTags(m.group(1),0);
                }

                //Split line into word format operands for instruction processing in data and code segments
                List<String> words = Arrays.asList(str.split("\\W+"));

                //Get message string within line and append binary ascii value
                if(words.contains("ascii")){
                    //Escape characters format correction
                    String valueInQuotes = StringUtils.substringBetween(str , "\"", "\"");
                    valueInQuotes = valueInQuotes.replace("\\n", "\r");
                    valueInQuotes = valueInQuotes.replace("\\0", "\0");

                    int byteCount = 0;

                    if(valueInQuotes != null){
                        byte[] bytes = valueInQuotes.getBytes(StandardCharsets.US_ASCII);
                        StringBuilder binary = new StringBuilder();
                        for (byte b : bytes){
                            byteCount++;
                            int val = b;
                            for (int i = 0; i < 8; i++)
                            {
                                binary.append((val & 128) == 0 ? 0 : 1);
                                val <<= 1;
                            }
                        }
                        resultString.append(binary);
                    }

                    tables.addData(words.get(0), byteCount);
                }

                //Add name and byte size to data segment table, append binary byte value
                else if(words.contains("byte")){
                    tables.addData(words.get(0), 1);
                    int intVal = Integer.parseInt(words.get(2));
                    resultString.append(StringUtils.leftPad(Integer.toBinaryString(intVal), 8, '0'));

                }else if(words.contains("half")){
                    tables.addData(words.get(0), 2);
                    int intVal = Integer.parseInt(words.get(2));
                    resultString.append(StringUtils.leftPad(Integer.toBinaryString(intVal), 16, '0'));

                }else if(words.contains("word")){
                    tables.addData(words.get(0), 4);
                    int intVal = Integer.parseInt(words.get(2));
                    resultString.append(StringUtils.leftPad(Integer.toBinaryString(intVal), 32, '0'));
                }

                //Code segment processing
                if (tables.getInstructions().containsKey(words.get(0))) {
                    //Instruction string to build
                    StringBuilder instString = new StringBuilder();

                    instString.append(tables.getInstructions().get(words.get(0)));

                    //Iterate instruction register operands
                    for(String arrstr : words) {
                        if (tables.getRegisters().containsKey(arrstr))
                            instString.append(tables.getRegisters().get(arrstr));
                    }

                    //Iterate instruction immediate operands
                    for(String arrstr : words) {
                        if (StringUtils.isNumeric(arrstr)){
                            int toint = Integer.parseInt(arrstr);
                            instString.append(StringUtils.leftPad(Integer.toBinaryString(toint), 16, '0'));
                        }
                    }

                    //Iterate data memory operands
                    for(String arrstr : words) {
                        if (tables.getData().containsKey(arrstr)){
                            int offset = 128;
                            for(String key : tables.getData().keySet()){
                                if(!arrstr.equals(key)){
                                    offset += tables.getData().get(key);
                                }else
                                    break;
                            }
                            instString.append(StringUtils.leftPad(Integer.toBinaryString(offset), 16, '0'));
                        }
                    }

                    //Each instruction iteration updates tag memory offset
                    for (Map.Entry<String, Integer> entry : tables.getTags().entrySet()) {
                        tables.addTags(entry.getKey(), entry.getValue()+1);
                    }

                    //Iterate tag memory operands
                    for(String arrstr : words) {
                        if (tables.getTags().containsKey(arrstr)) {
                            int offset = -tables.getTags().get(arrstr);
                            instString.append(StringUtils.right(Integer.toBinaryString(offset+1), 16));
                        }
                    }

                    //Pad instruction to size 32 bits
                    String paddedStr = StringUtils.rightPad(instString.toString(), 32, '0');
                    resultString.append(paddedStr);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return resultString;
    }
}