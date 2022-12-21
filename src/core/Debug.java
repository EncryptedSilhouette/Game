package core;

import java.util.ArrayList;

public class Debug {

    //Debug Stats

    //Debug Log
    StringBuilder debugLog;

    public static ArrayList<String> lines = new ArrayList<>();

    public static void log(String message) {
    }

    public static String getLog() {
        StringBuilder log = new StringBuilder();
        for (String line: lines) {
            log.append(line);
        }
        return log.toString();
    }
}
