import java.io.File;
import java.util.Scanner;

public class TextFileManipulationScannerLoop {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\pc1\\Desktop\\chelo.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
            System.out.println(sc.nextLine());
    }
}
