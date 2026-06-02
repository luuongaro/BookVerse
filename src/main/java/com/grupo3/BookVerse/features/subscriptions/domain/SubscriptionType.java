package com.grupo3.BookVerse.features.subscriptions.domain;

public enum SubscriptionType {
    FREE("Free"),
    PREMIUM("Premium");

    private final String displayName;

    SubscriptionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
