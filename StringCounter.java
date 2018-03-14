

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class StringCounter {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Example: \njava StringCounter /code/java/MyExampleProject");
            System.exit(0);
        }

        // Scanner scanner = new Scanner(System.in);

        // System.out.println("Please enter Android project path:");

        String path;
        while ((path = args[0]).length() == 0) {
            error("Please enter correct android project path");
        }

        File file = new File(path);

        if (!file.exists()) {
            error("File not exists");
        }

        analyzeOldProject(file);


        for (String subPath : file.list()) {
            File subPathFile = new File(file, subPath);
            if (subPathFile.isDirectory()) {
                List<String> content = Arrays.asList(subPathFile.list());
                String moduleIndicator = subPath + ".iml";
                if (content.contains(moduleIndicator)) {
                    System.out.println(Color.ANSI_BLUE + "In module: " + subPath + Color.ANSI_BLUE);
                    analyzeProject(subPathFile);
                }
            }
        }
    }

    private static void analyzeOldProject(File file) {
        String filename = file.getName();
        boolean isOldProject = false;
        for (String fn : file.list()) {
            String moduleIndicator = fn + ".iml";
            if (moduleIndicator.contains(filename)) {
                isOldProject = true;
            }
        }

        if (!isOldProject) return;

        List<String> list = gradleFlavours(new File(file, "build.gradle"));

        list.add("/");

        SourceSet ssMain = new SourceSet(SourceType.src, list, file.getPath());
        System.out.println(Color.ANSI_YELLOW + ssMain + Color.ANSI_YELLOW);

        SourceSet ssRes = new SourceSet(SourceType.res, list, file.getPath());
        System.out.println(Color.ANSI_YELLOW + ssRes + Color.ANSI_YELLOW);
    }

    private static void analyzeProject(File file) {
        List<String> list = gradleFlavours(new File(file, "build.gradle"));

        list.add("main");

        SourceSet ssMain = new SourceSet(SourceType.java, list, new File(file, "src").getPath());
        System.out.println(Color.ANSI_YELLOW + ssMain + Color.ANSI_YELLOW);

        SourceSet ssRes = new SourceSet(SourceType.res, list, new File(file, "src").getPath());
        System.out.println(Color.ANSI_YELLOW + ssRes + Color.ANSI_YELLOW);
    }


    private static List<String> gradleFlavours(File file) {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();

        String gradle = readFile(file);

        GradleNode node = new GradleNode("root", gradle);

        GradleNode a = node.byName("productFlavors");

        if (a != null) {
            return a.childNames();
        }


        return list;
    }

    private static void error(String error) {
        System.out.println(error);
        System.exit(0);
    }

    private static String readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader((file.getPath())));
            StringBuilder sb = new StringBuilder();
            String string;

            while ((string = reader.readLine()) != null) {
                sb.append(string).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}