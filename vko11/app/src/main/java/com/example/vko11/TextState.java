package com.example.vko11;

public class TextState {

    private String readableText = "";
    private String editableText = "";
    private String displayText = "display text";

    public static TextState textState = new TextState();

    public static TextState getInstance() {
        return textState;
    }

    public String getReadableText() {
        return readableText;
    }

    public void setReadableText(String readableText) {
        this.readableText = readableText;
    }

    public String getEditableText() {
        return editableText;
    }

    public void setEditableText(String editableText) {
        this.editableText = editableText;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
