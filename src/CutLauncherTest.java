import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CutLauncherTest {

    private static String textOfFile(String filePath) throws Exception{
        File file = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new Exception("File is not found");
        }
        StringBuilder stringBuilder = new StringBuilder("");
        while (scanner.hasNextLine()){
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String[] args1 = {"-c", "-o", "src/files/output1.txt", "src/files/input.txt", "6-13"};
    private String[] args2 = {"-w", "-o", "src/files/output2.txt", "src/files/input.txt", "2-"};
    private String[] args3 = {"-w", "-o", "src/files/output3.txt", "src/files/input.txt", "-4"};

    @Test
    void test1() throws Exception {
        CutLauncher.main(args1);
        assertEquals(textOfFile("src/files/expectedOutput1.txt"), textOfFile("src/files/output1.txt"));
    }

    @Test
    void test2() throws Exception{
        CutLauncher.main(args2);
        assertEquals(textOfFile("src/files/expectedOutput2.txt"), textOfFile("src/files/output2.txt"));
    }

    @Test
    void test3() throws Exception{
        CutLauncher.main(args3);
        assertEquals(textOfFile("src/files/expectedOutput3.txt"), textOfFile("src/files/output3.txt"));
    }
}