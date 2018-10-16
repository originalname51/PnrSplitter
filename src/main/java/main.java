import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class main {
    public static void main(String args[]) throws IOException {

        int pnrCount = 1;
        final int NUMBER_OF_PNR = 10;

        for (int i = 1; i <= NUMBER_OF_PNR; i++) {
            String fileName = "C:\\Users\\Rob\\Development\\SeveralPnrFilesAsOneTextFile\\"+i + "PNR";
            //read file into stream, try-with-resources
             try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
                boolean pnrDetected = false;
                StringBuilder sb = new StringBuilder();
                for (String line : iterableOf(lines)) {
                    if (line.contains("UNB+")) {
                        sb.append("UNA:+.?*'");
                        sb.append("\n");
                        pnrDetected = true;
                    }
                    if (pnrDetected) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    if (line.contains("UNZ+")) {
                        createFileUsingFileClass(sb.toString(), "C:\\Users\\Rob\\Development\\SplitPnrs\\pnr" + pnrCount + ".txt");
                        pnrDetected = false;
                        pnrCount++;
                        sb = new StringBuilder();
                    }
                }
            }
        }
    }

    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }
    private static void createFileUsingFileClass(String stringFile, String fileName) throws IOException
    {
        File file = new File(fileName);
        //Create the file
        if (file.createNewFile()){
            System.out.println("File is created!");
        }else{
            System.out.println("File already exists.");
        }
        FileWriter writer = new FileWriter(file);
        writer.write(stringFile);
        writer.close();
    }

}
