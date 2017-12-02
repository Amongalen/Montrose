package Decryptor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 *
 * @author Adam Parys
 */
public class Decryptor {

    public static void main(String args[]) throws IOException {
        String path = readPath();
        Decryptor decryptor = new Decryptor(path);
        decryptor.decrypt();
    }

    private static String readPath() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input path to the input folder");
        String path = br.readLine();
        if (!new File(path).isDirectory()) {
            System.out.println("It must be a valid path to a directory, try again.");
            return readPath();
        }
        return path;
    }
    final static String OUTPUTFILE_STRING = "out.txt";
    String pathToFolder;
    int maxStringsInMemory;
    HashMap<String, Integer> amountOfLinesParsedInFiles;
    LinkedList<File> filesParsed;
    int firstLineToBeParsed = 0;

    public Decryptor(String pathToFolder) {
        this.pathToFolder = pathToFolder;
        maxStringsInMemory = (int) (Runtime.getRuntime().maxMemory() / 10000);
    }

    public void decrypt() {
        deletePrevFile();
        amountOfLinesParsedInFiles = new HashMap<>();
        filesParsed = new LinkedList<>();
        readFile();
    }

    private void readFile() {
        HashMap<Integer, String> stringsBuffer = new HashMap<>();
        File directory = new File(pathToFolder);
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isFile() && !filesParsed.contains(file)) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    int lineCount = 0;
                    while ((line = br.readLine()) != null) {
                        lineCount++;
                        if (amountOfLinesParsedInFiles.containsKey(file.getName()) && lineCount <= amountOfLinesParsedInFiles.get(file.getName())) {
                            continue;
                        }
                        String[] lineParts = line.split(" ");
                        if (Integer.parseInt(lineParts[0]) > firstLineToBeParsed + maxStringsInMemory) {
                            amountOfLinesParsedInFiles.put(file.getName(), lineCount - 1);
                            break;
                        } else {
                            stringsBuffer.put(Integer.parseInt(lineParts[0]), lineParts[1] + "\n");
                        }

                    }
                    if (line == null) {
                        filesParsed.add(file);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        writeToFile(stringsBuffer);
        firstLineToBeParsed += maxStringsInMemory;
        if (filesParsed.size() != fList.length) {
            readFile();
        }
    }

    private void deletePrevFile() {
        File outFile = new File(OUTPUTFILE_STRING);
        if (outFile.exists()) {
            outFile.delete();
        }
    }

    private void writeToFile(HashMap<Integer, String> strings) {
        try (
                FileOutputStream fos = new FileOutputStream(OUTPUTFILE_STRING, true);
                FileChannel fileChannel = fos.getChannel();) {
            for (int i = firstLineToBeParsed + 1; i <= firstLineToBeParsed + strings.size(); i++) {
                byte[] inputBytes = strings.get(i).getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(inputBytes);
                fileChannel.write(buffer);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
