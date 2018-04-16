import org.kohsuke.args4j.*;
import java.util.regex.*;

public class CutLauncher {

    @Option(name = "-c", usage = "char indentation", forbids = "-w")
    private boolean charIndentation;

    @Option(name = "-w", usage = "word indentation", forbids = "-c")
    private boolean wordIndentation;

    @Option(name = "-o", usage = "output file path")
    private String outputFilePath = "";

    @Argument (usage = "input file path", index = 0)
    private String inputFilePath = "";

    @Argument (usage = "indentation range", index = 1)
    private String range = "";


    public static void main(String[] args) throws Exception{
        new CutLauncher().launch(args);
    }

    private void launch(String[] args) throws Exception{

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            e.printStackTrace();
            return;
        }

        if ((charIndentation && wordIndentation) ||
                (!charIndentation && !wordIndentation))
            throw new IllegalArgumentException();

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
        Matcher matcher;

        matcher = format1.matcher(args[args.length-1]);
        if (matcher.matches()) {
            if (digitsMatcher.find())
                range[0] = Integer.parseInt(digitsMatcher.group());
        }
        else {
            matcher = format2.matcher(args[args.length-1]);
            if (matcher.matches()) {
                if (digitsMatcher.find())
                    range[1] = Integer.parseInt(digitsMatcher.group());
            }
            else {
                matcher = format3.matcher(args[args.length-1]);
                if (matcher.matches()) {
                    int i = 0;
                    while (digitsMatcher.find()){
                        range[i] = Integer.parseInt(digitsMatcher.group());
                        i++;
                    }
                    if (range[0] >= range[1])
                        throw new Exception("First number has to be less than second");
                }
                else
                    throw new Exception("Wrong format of range");
            }
        }

        Cut.toCut(charIndentation, outputFilePath, inputFilePath, range);
    }
}
