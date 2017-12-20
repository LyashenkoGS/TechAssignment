package com.lyashenkogs.searchengineclient.document;

import java.util.Objects;

public class Document {
    private String key;
    private String document;

    public Document(String key, String document) {
        this.key = key;
        this.document = document;
    }

    public Document() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "Document{" +
                "key='" + key + '\'' +
                ", document='" + document + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document1 = (Document) o;
        return Objects.equals(key, document1.key) &&
                Objects.equals(document, document1.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, document);
    }
}
