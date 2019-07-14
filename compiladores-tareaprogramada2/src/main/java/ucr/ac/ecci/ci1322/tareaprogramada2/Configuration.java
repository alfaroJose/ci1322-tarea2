package ucr.ac.ecci.ci1322.tareaprogramada2;

public class Configuration {

    private String sourceFileName;
    private String sourceFilePath;
    private String targetFileName;
    private String targetFilePath;

    public Configuration(){
        askFileInfo();
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