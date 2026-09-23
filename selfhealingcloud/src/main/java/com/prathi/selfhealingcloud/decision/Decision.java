package com.prathi.selfhealingcloud.decision;

public class Decision {

    private final String rootCause;
    private final String action;
    private final double confidence;

    public Decision(String rootCause, String action, double confidence) {
        this.rootCause = rootCause;
        this.action = action;
        this.confidence = confidence;
    }

    public String getRootCause() {
        return rootCause;
    }

    public String getAction() {
        return action;
    }

    public double getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        return String.format(
                "RootCause: %s | Action: %s | Confidence: %.2f%%",
                rootCause, action, confidence
        );
    }
}
