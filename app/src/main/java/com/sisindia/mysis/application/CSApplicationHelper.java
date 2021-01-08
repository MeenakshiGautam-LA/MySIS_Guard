package com.sisindia.mysis.application;

public class CSApplicationHelper {
    public static CSApplication MMApplication;

    public static CSApplication application() {

        return MMApplication;
    }

    public static void setCSApplication(CSApplication application) {
        MMApplication = application;
    }
}
