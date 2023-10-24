package com.ondre.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * class with tools to find process with most resources used
 */
public class Activity {
    /**
     * constructor
     */
    public Activity() {
    }

    private String getProcessListCommand(){
        String osName = System.getProperty("os.name").toLowerCase();

        // For Windows
        if (osName.contains("win")) {
            return "tasklist";
        }// For Mac, Linux, and Unix
        else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux")) {
            throw new UnsupportedOperationException("Unsupported OS: " + osName);
            //TODO:return "ps -e"; support for mac linux and unix
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
    /**
     * sorts processList, find Biggest resources
     * @return process with most resources used
     * */
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

