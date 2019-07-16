package ucr.ac.ecci.ci1322.tareaprogramada2.compiler.symboltable;

import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, String> symbolTable;

    public SymbolTable(){
        symbolTable = new HashMap<String, String>();
    }

    public void addSymbol(String key, String value) {
        symbolTable.put(key,value);
    }

    public boolean findSymbol(String key){
        return symbolTable.containsKey(key);
    }

    public String getValue(String key){
        return symbolTable.get(key);
    }

    public void print(){
        for (String key : symbolTable.keySet()) {
            System.out.println(key);
        }
    }
}