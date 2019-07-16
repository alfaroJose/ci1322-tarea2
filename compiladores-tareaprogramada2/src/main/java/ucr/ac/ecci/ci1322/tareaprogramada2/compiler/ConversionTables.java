package ucr.ac.ecci.ci1322.tareaprogramada2.compiler;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ConversionTables {

    private HashMap<String, String> registers;
    private HashMap<String, String> instructions;

    //LinkedHashMap for preserving insertion order
    private LinkedHashMap<String, Integer> data;
    private HashMap<String, Integer> tags;

    public ConversionTables (){

        registers = new HashMap<String, String>();
        instructions = new HashMap<String, String>();
        data = new LinkedHashMap<String, Integer>();
        tags = new HashMap<String, Integer>();

        //Registers

        registers.put("r0", "00000");
        registers.put("r1", "00001");
        registers.put("r2", "00010");
        registers.put("r3", "00011");
        registers.put("r4", "00100");
        registers.put("r5", "00101");
        registers.put("r6", "00110");
        registers.put("r7", "00111");
        registers.put("r8", "01000");
        registers.put("r9", "01001");
        registers.put("r10", "01010");
        registers.put("r11", "01011");
        registers.put("r12", "01100");
        registers.put("r13", "01101");
        registers.put("r14", "01111");
        registers.put("r15", "11111");
        registers.put("r16", "10000");
        registers.put("r17", "10001");
        registers.put("r18", "10010");
        registers.put("r19", "10011");
        registers.put("r20", "10100");
        registers.put("r21", "10101");
        registers.put("r22", "10110");
        registers.put("r23", "10111");
        registers.put("r24", "11000");
        registers.put("r25", "11001");
        registers.put("r26", "11010");
        registers.put("r27", "11011");
        registers.put("r28", "11100");
        registers.put("r29", "11101");
        registers.put("r30", "11110");
        registers.put("r31", "11111");


        //Instructions
        //Arithmetic

        instructions.put("add", "000000");
        instructions.put("addi", "000001");
        instructions.put("sub", "000010");
        instructions.put("subi", "000011");
        instructions.put("mul", "000100");
        instructions.put("muli", "000101");
        instructions.put("div", "000110");
        instructions.put("divi", "000111");
        instructions.put("mod", "001000");
        instructions.put("modi", "001001");

        //Logical

        instructions.put("and", "001010");
        instructions.put("andi", "001011");
        instructions.put("or", "001100");
        instructions.put("ori", "001101");
        instructions.put("xor", "001110");
        instructions.put("xori", "001111");
        instructions.put("not", "010000");
        instructions.put("noti", "010001");

        //Shift

        instructions.put("sal", "010010");
        instructions.put("sali", "010011");
        instructions.put("sar", "010100");
        instructions.put("sari", "010101");
        instructions.put("sll", "010110");
        instructions.put("slli", "010111");
        instructions.put("slr", "011000");
        instructions.put("slri", "011001");
        instructions.put("scl", "011010");
        instructions.put("scli", "011011");
        instructions.put("scr", "011100");
        instructions.put("scr", "011101");

        //Load

        instructions.put("lsb", "100000");
        instructions.put("lub", "100001");
        instructions.put("lsh", "100010");
        instructions.put("luh", "100011");
        instructions.put("lsw", "100100");
        instructions.put("luw", "100101");

        //Store

        instructions.put("sb", "100110");
        instructions.put("sh", "100111");
        instructions.put("sw", "101000");

        //Control

        instructions.put("jmp", "101001");
        instructions.put("je", "101010");
        instructions.put("jne", "101011");
        instructions.put("jg", "101100");
        instructions.put("jges", "101101");
        instructions.put("jgeu", "101110");
        instructions.put("jls", "101111");
        instructions.put("jlu", "110000");
        instructions.put("jles", "110001");
        instructions.put("jleu", "110010");

        instructions.put("call", "110011");
        instructions.put("ret", "110101");
        instructions.put("syscall", "110110");

    }

    public HashMap<String, String> getInstructions() {
        return instructions;
    }

    public HashMap<String, String> getRegisters() {
        return registers;
    }

    public LinkedHashMap<String, Integer> getData() {
        return data;
    }

    public void addData(String key, int value) {
        data.put(key,value);
    }

    public HashMap<String, Integer> getTags() {
        return tags;
    }

    public void addTags(String key, int value) {
        tags.put(key,value);
    }
}