package com.example.application.data;

public enum AgeCategory {
    FROM3TO5("3-5"),
    FROM6TO8("6-8"),
    FROM9TO12("9-12"),
    FROM13TO15("13-15"),
    FROM16TO18("16-18");

    private final String displayName;

    AgeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}