import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cut{

    public static void toCut(boolean charIndentation, String outputFilePath,
                             String inputFilePath, int[] range) throws IOException {

        File inputFile = new File(inputFilePath);
        Scanner scanner = new Scanner(inputFile);
        File outputFile;
        PrintWriter printWriter;
        String line;
        String substring;
        if (!outputFilePath.isEmpty()) {
            outputFile = new File(outputFilePath);
            printWriter = new PrintWriter(outputFile);
        }
        else
            printWriter = new PrintWriter(System.out);

        if (charIndentation) {
            if (range[0] == -1) {
                while (scanner.hasNextLine()) {
                    int end = range[1];
                    line = scanner.nextLine();
                    if (end >= line.length())
                        end = line.length();
                    substring = line.substring(0, end);
                    if (!substring.isEmpty())
                        printWriter.println(substring);
                }
            } else {
                if (range[1] == -1) {
                    while (scanner.hasNextLine()) {
                        int begin = range[0];
                        line = scanner.nextLine();
                        if (begin >= line.length())
                            continue;
                        substring = line.substring(begin);
                        if (!substring.isEmpty())
                            printWriter.println(substring);
                    }
                } else {
                    while (scanner.hasNextLine()) {
                        int begin = range[0];
                        int end = range[1];
                        line = scanner.nextLine();
                        if (begin >= line.length())
                            continue;
                        if (end >= line.length())
                            end = line.length() - 1;
                        substring = line.substring(begin, end);
                        if (!substring.isEmpty())
                            printWriter.println(substring);
                    }
                }
            }
        } else {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] words = line.split("\\s+");
                List<Integer> indexOfWords = new ArrayList<>();
                int fromIndex = 0;
                for (String word: words) {
                    if (word.isEmpty())
                        continue;
                    indexOfWords.add(line.indexOf(word, fromIndex));
                    fromIndex = line.indexOf(word, fromIndex) + word.length();
                }

                int begin;
                int end;
                if (range[0] == -1) {
                    if (range[1] >= indexOfWords.size())
                        end = line.length();
                    else
                        end = indexOfWords.get(range[1]);
                    substring = line.substring(0, end);
                    if (!substring.isEmpty())
                        printWriter.println(substring);
                } else {
                    if (range[1] == -1) {
                        if (range[0] >= indexOfWords.size())
                            continue;
                        else
                            begin = indexOfWords.get(range[0]);
                        substring = line.substring(begin);
                        if (!substring.isEmpty())
                            printWriter.println(substring);
                    } else {
                        if (range[0] >= indexOfWords.size())
                            continue;
                        else
                            begin = indexOfWords.get(range[0]);
                        if (range[1] >= indexOfWords.size())
                            end = line.length();
                        else
                            end = indexOfWords.get(range[1]);
                        substring = line.substring(begin, end);
                        if (!substring.isEmpty())
                            printWriter.println(substring);
                    }
                }
            }
        }

        scanner.close();
        printWriter.close();
    }
}
