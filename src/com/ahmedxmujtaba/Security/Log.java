package com.ahmedxmujtaba.Security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log implements Serializable {
    private static final String LOG_FILE = "src/com/ahmedxmujtaba/Security/Data/log.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log() {
        ensureLogFileExists();
    }

    // Method to log messages
    public static void logMessage(String message) {
        writeToFile("MESSAGE", message);
    }

    // Method to log errors
    public static void logError(String error) {
        writeToFile("ERROR", error);
    }

    // Method to log exceptions
    public static void logException(Exception exception) {
        writeToFile("EXCEPTION", exception.toString());
    }

    // Helper method to write to file
    private static void writeToFile(String logType, String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            pw.println(timestamp + " [" + logType + "] " + message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    // Ensure the log file exists, create if it doesn't
    private void ensureLogFileExists() {
        File logFile = new File(LOG_FILE);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                System.out.println("Log file created: " + LOG_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
