import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class CutLauncher {

    public static void main(String[] args) throws Exception{

        final String inputFilePath;
        final String outputFilePath;
        boolean charIndentation = false;
        final String commandLineFormat = "\n[-c|-w] \"input file path\" range" +
                "\n[-c|-w] -o \"output file path\" \"input file path\" range";

        if (args.length < 3)
            throw new Exception(commandLineFormat);
        if (args[0].equals("-c"))
            charIndentation = true;
        else {
            /* charIndentation == false ~ wordIndentation == true */
            if (!args[0].equals("-w"))
                throw new Exception("Wrong indentation flag");
        }


        Pattern fileAbsolutePathFormat =
                Pattern.compile("([A-Z]:\\\\(([^\\\\/:*?\"<>|]+)\\\\)+)[^\\\\/:*?\"<>|]+.txt");
        Pattern fileRelativePathFormat =
                Pattern.compile("(([^\\\\/:*?\"<>|]+)/)+[^\\\\/:*?\"<>|]+.txt");
        Matcher matcher1;
        Matcher matcher2;
        List<String> filePaths = new ArrayList<>();

        if (args[1].equals("-o"))
            for (int i = 2; i < args.length-1; i++)
                filePaths.add(args[i]);
        else
            for (int i = 1; i < args.length-1; i++)
                filePaths.add(args[i]);

        for (String filePath: filePaths){
            matcher1 = fileAbsolutePathFormat.matcher(filePath);
            matcher2 = fileRelativePathFormat.matcher(filePath);
            if (!matcher1.matches() && !matcher2.matches())
                throw new Exception(commandLineFormat);
        }

        switch (args[1]){
            case "-o":
                if (filePaths.size() != 2)
                    throw new Exception(commandLineFormat);
                outputFilePath = filePaths.get(0);
                inputFilePath = filePaths.get(1);
                if (outputFilePath.equals(inputFilePath))
                    throw new Exception("Paths are same");
                break;
            default:
                if (filePaths.size() != 1)
                    throw new Exception("Wrong command line");
                outputFilePath = null;
                inputFilePath = filePaths.get(0);
                break;
        }


        int[] range = {-1, -1};
        Pattern digits = Pattern.compile("\\d+");
        Matcher digitsMatcher;
        try {
            digitsMatcher = digits.matcher(args[args.length-1]);
        }
        catch (Exception e){
            throw new Exception("No digits in range");
        }
        Pattern format1 = Pattern.compile("(\\d+)-");
        Pattern format2 = Pattern.compile("-(\\d+)");
        Pattern format3 = Pattern.compile("(\\d+)-(\\d+)");

        while (true) {
            matcher1 = format1.matcher(args[args.length-1]);
            if (matcher1.matches()) {
                if (digitsMatcher.find())
                    range[0] = Integer.parseInt(digitsMatcher.group());
                break;
            }
            matcher1 = format2.matcher(args[args.length-1]);
            if (matcher1.matches()) {
                if (digitsMatcher.find())
                    range[1] = Integer.parseInt(digitsMatcher.group());
                break;
            }
            matcher1 = format3.matcher(args[args.length-1]);
            if (matcher1.matches()) {
                int i = 0;
                while (digitsMatcher.find()){
                    range[i] = Integer.parseInt(digitsMatcher.group());
                    i++;
                }
                if (range[0] >= range[1])
                    throw new Exception("First number has to be less than second");
                break;
            }
            throw new Exception("Wrong format of range");
        }

        Cut cut = new Cut(charIndentation, outputFilePath, inputFilePath, range);
        cut.toCut();
    }
}
