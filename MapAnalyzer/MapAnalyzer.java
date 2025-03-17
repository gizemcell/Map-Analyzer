import java.util.Locale;

public class MapAnalyzer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        String[] inputContent = FileInput.readFile(args[0], true, true);
        PathProcessor.processPaths(inputContent,args[1]);
    }
}
