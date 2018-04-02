import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class CutLauncher {

    public static void main(String[] args) throws Exception{

        String inputFilePath;
        String outputFilePath;
        boolean charIndentation = false;


        if (args.length < 3)
            throw new Exception("Wrong command line");
        if (args[0].equals("-c"))
            charIndentation = true;
        else {
            /* charIndentation == false ~ wordIndentation == true */
            if (!args[0].equals("-w"))
                throw new Exception("Wrong indentation flag");
        }


        Pattern filePathFormat =
                Pattern.compile("(([A-Z]:\\\\(([^\\\\/:*?\"<>|]+)\\\\)+)[^\\\\/:*?\"<>|]+.txt)" + "|"
                        + "((([^\\\\/:*?\"<>|]+)/)+[^\\\\/:*?\"<>|]+.txt)");
        Pattern filePathsFormat =
                Pattern.compile("^((([A-Z]:\\\\(([^\\\\/:*?\"<>|]+)\\\\)+)[^\\\\/:*?\"<>|]+.txt\\s?)" + "|"
                        + "((([^\\\\/:*?\"<>|]+)/)+[^\\\\/:*?\"<>|]+.txt\\s?)){1,2}$");
        Matcher matcher;
        StringBuilder filePathsBuilder = new StringBuilder("");
        String filePaths;
        List<String> filePathsList = new ArrayList<>();

        if (args[1].equals("-o"))
            for (int i = 2; i < args.length-1; i++)
                filePathsBuilder.append(args[i]).append(" ");
        else
            for (int i = 1; i < args.length-1; i++)
                filePathsBuilder.append(args[i]).append(" ");

        filePaths = filePathsBuilder.toString();
        matcher = filePathsFormat.matcher(filePaths);
        if (!matcher.matches())
            throw new Exception("Wrong command line");
        matcher = filePathFormat.matcher(filePaths);
        while (matcher.find())
            filePathsList.add(matcher.group());

        switch (args[1]){
            case "-o":
                if (filePathsList.size() != 2)
                    throw new Exception("Wrong command line");
                outputFilePath = filePathsList.get(0);
                inputFilePath = filePathsList.get(1);
                break;
            default:
                if (filePathsList.size() != 1)
                    throw new Exception("Wrong command line");
                outputFilePath = null;
                inputFilePath = filePathsList.get(0);
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
            matcher = format1.matcher(args[args.length-1]);
            if (matcher.matches()) {
                if (digitsMatcher.find())
                    range[0] = Integer.parseInt(digitsMatcher.group());
                break;
            }
            matcher = format2.matcher(args[args.length-1]);
            if (matcher.matches()) {
                if (digitsMatcher.find())
                    range[1] = Integer.parseInt(digitsMatcher.group());
                break;
            }
            matcher = format3.matcher(args[args.length-1]);
            if (matcher.matches()) {
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
    }
}
