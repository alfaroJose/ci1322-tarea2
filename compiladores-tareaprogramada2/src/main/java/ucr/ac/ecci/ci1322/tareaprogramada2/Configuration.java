package ucr.ac.ecci.ci1322.tareaprogramada2;

public class Configuration {

    private String sourceFileName;
    private String sourceFilePath;
    private String targetFileName;
    private String targetFilePath;

    public Configuration(){
        //askFileInfo();
        prueba();
        //pruebaJose();
    }

    private void prueba (){
        sourceFileName = "myfile.txt";
        sourceFilePath = "/Users/Andres/Desktop/myfile.txt";
        targetFileName =  "res.txt";
        targetFilePath = "C:\\Users\\Andres\\Desktop\\ci1322-tarea2\\compiladores-tareaprogramada2\\src\\main\\java\\ucr\\ac\\ecci\\ci1322\\tareaprogramada2\\compiler";
    }

    private void pruebaJose(){
        sourceFilePath = "C:\\Users\\josea\\Desktop\\Tarea Programada II\\compiladores-tareaprogramada2\\programa mock.asm";
    }

    private void askFileInfo(){
        java.util.Scanner scan = new java.util.Scanner(System.in);
        System.out.println("Insert the file name of the source program: ");
        sourceFileName = scan.nextLine();
        System.out.println("Insert the file path where the source program is: ");
        sourceFilePath = scan.nextLine();
        System.out.println("Insert the file name of the new compiled program: ");
        targetFileName = scan.nextLine();
        System.out.println("Insert the file path where you want to be the new compiled program: ");
        targetFilePath = scan.nextLine();
        System.out.println("[INFO]File configuration done");
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }
}