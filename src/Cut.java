import java.io.*;
import java.util.Scanner;

public class Cut{

    private boolean charIndentation;
    private String outputFilePath;
    private String inputFilePath;
    private int[] range;

    public Cut(boolean charIndentation, String outputFilePath,
               String inputFilePath, int[] range) {

        this.charIndentation = charIndentation;
        this.outputFilePath = outputFilePath;
        this.inputFilePath = inputFilePath;
        this.range = range;
    }

    public void toCut() throws FileNotFoundException {

        File inputFile = new File(inputFilePath);
        Scanner scanner = new Scanner(inputFile);
        File outputFile;
        PrintWriter printWriter = null;
        String line;
        String substring;
        if (outputFilePath != null) {
            outputFile = new File(outputFilePath);
            printWriter = new PrintWriter(outputFile);
        }


        if (charIndentation){
            if (range[0] == -1){
                while (scanner.hasNextLine()) {
                    int end = range[1];
                    line = scanner.nextLine();
                    if (end >= line.length())
                        end = line.length();
                    substring = line.substring(0, end);
                    if (!substring.isEmpty()) {
                        if (outputFilePath != null)
                            printWriter.println(substring);
                        else
                            System.out.println(substring);
                    }
                }
            }
            else {
                if (range[1] == -1){
                    while (scanner.hasNextLine()) {
                        int begin = range[0];
                        line = scanner.nextLine();
                        if (begin >= line.length())
                            continue;
                        substring = line.substring(begin, line.length());
                        if (!substring.isEmpty()) {
                            if (outputFilePath != null)
                                printWriter.println(substring);
                            else
                                System.out.println(substring);
                        }
                    }
                }
                else {
                    while (scanner.hasNextLine()) {
                        int begin = range[0];
                        int end = range[1];
                        line = scanner.nextLine();
                        if (begin >= line.length())
                            continue;
                        if (end >= line.length())
                            end = line.length() - 1;
                        substring = line.substring(begin, end);
                        if (!substring.isEmpty()) {
                            if (outputFilePath != null)
                                printWriter.println(substring);
                            else
                                System.out.println(substring);
                        }
                    }
                }
            }
        }
        /* else {
            while (scanner.hasNextLine()) {

            }
        } */
        scanner.close();
        if (outputFilePath != null)
            printWriter.close();
    }

}
