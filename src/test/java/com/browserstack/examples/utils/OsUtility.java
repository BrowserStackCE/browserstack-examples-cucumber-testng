package com.browserstack.examples.utils;

public class OsUtility {
    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS.toLowerCase();
    }

    public static boolean isWindows() {
        return (getOsName().contains("win"));
    }

    public static boolean isUnix() {
        return (getOsName().contains("nix")
                || getOsName().contains("nux")
                || getOsName().contains("aix"));
    }

    public static boolean isMac() {
        return (getOsName().contains("mac"));
    }
}
