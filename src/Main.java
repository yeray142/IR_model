import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import irmodel.BooleanModel;

public class Main {
    private static final String DEFAULT_FOLDER_PATH = "src/data/review";
    
    public static void main(String[] args) {
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        BooleanModel booleanModel = new BooleanModel();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter folder path (leave it blank for default):");
        System.out.println("(Default: '" + DEFAULT_FOLDER_PATH + "')");
        String folderPath = scanner.nextLine();
        if (folderPath.equals("")) {
            folderPath = DEFAULT_FOLDER_PATH;
        }
        File folder = new File(folderPath);
        if (!folder.exists()) {
            System.out.println("Folder does not exist!");
        }
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("No files found in the folder!");
            return;
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    String[] terms = content.split("\\s+");
                    booleanModel.addDocument(i, terms);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Enter search term:");
        String query = scanner.nextLine();
        List<Integer> results = booleanModel.search(query);
        if (results == null) {
            System.out.println("No results found!");
        } else {
            System.out.println("Search results:");
            for (int docId : results) {
                System.out.println(listOfFiles[docId].getName());
            }
        }
    }
}
