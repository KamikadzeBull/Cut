public class Cut {

    private boolean charIndentation;
    private String outputFilePath;
    private String inputFilePath;
    private int[] range;


    public Cut(boolean charIndentation, String outputFilePath,
               String inputFilePath, int[] range){

        this.charIndentation = charIndentation;
        this.outputFilePath = outputFilePath;
        this.inputFilePath = inputFilePath;
        this.range = range;
    }

}
