public class Utils {
    
    public static void openTerminal() throws Exception {
        Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe");
        p.waitFor();
    }
}
