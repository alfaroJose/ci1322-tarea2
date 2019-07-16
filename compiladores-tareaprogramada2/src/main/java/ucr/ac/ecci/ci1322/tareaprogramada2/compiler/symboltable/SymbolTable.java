package ucr.ac.ecci.ci1322.tareaprogramada2.compiler.symboltable;

import java.util.HashMap;

/**
 * Class that stores identifiers that the multiple phases of the compiler find in a HashMap
 */
public class SymbolTable {

    private HashMap<String, String> symbolTable;

    public SymbolTable(){
        symbolTable = new HashMap<>();
    }

    /**
     * Method that adds a symbol to the map
     * @param key the key of the entry
     * @param value the value of the entry
     */
    public void addSymbol(String key, String value) {
        symbolTable.put(key,value);
    }

    /**
     * Method checks if a symbol is present in the map
     * @param key the key to search
     * @return true if it is present, false otherwise
     */
    public boolean findSymbol(String key){
        return symbolTable.containsKey(key);
    }
}