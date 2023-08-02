package org.example.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private static String getProcessListCommand(){
        String osName = System.getProperty("os.name").toLowerCase();

        // For Windows
        if (osName.contains("win")) {
            return "tasklist";
        }// For Mac, Linux, and Unix
        else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux")) {
            return "ps -e";
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + osName);
        }
    }
    private List<String> getProcessList(){
        String command = getProcessListCommand();
        List<String> list = new ArrayList<>();
        try {
            Process process = new ProcessBuilder(command.split("\\s+")).start();
            try (InputStream inputStream = process.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("ProcessList completed with exit code: " + exitCode);

            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void sortProcessList(){
        List<String> list = getProcessList();
        System.out.println(list);
    }
}
