package com.codingblocks.recorder;

public class Record {
    private String name;
    private String path;
    private int id;
    private int length;
    private long date;

    public Record() {
    }

    public Record(String name, String path, int id, int length, long date) {
        this.name = name;
        this.path = path;
        this.id = id;
        this.length = length;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public long getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
