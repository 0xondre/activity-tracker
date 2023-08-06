package com.ondre.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Activity {
    public Activity() {
    }

    private String getProcessListCommand(){
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
                    if(!line.contains("Console")){
                        continue;
                    }
                    list.add(line);
                }
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("ProcessList completed with exit code: " + exitCode);

            return list;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String sortProcessList(){
        List<String> list = getProcessList();
        String biggestUsage = "";
        int tmp = 0;
        for (String line : list) {
            String[] parts = line.split("\\s+");
            parts[4] = parts[4].replaceAll("[^a-zA-Z0-9]", "");
            int value = Integer.parseInt(parts[4].replaceAll("[a-zA-Z]", "0"));
            if(tmp < value){
                tmp = value;
                biggestUsage = parts[0];
            }
        }
        return biggestUsage;
    }
}

