package com.gmail.erofeev.st.alexei.fourthweek.controller.util;

import java.util.List;

import static java.util.Arrays.asList;

public class Paginator {
    private int page;
    private int maxPage;
    private int size;
    private final List<String> droppedListValues = asList("2", "5", "10", "20");

    public Paginator(int current, int maxPage, int size) {
        this.page = current;
        this.maxPage = maxPage;
        this.size = size;
    }

    public Paginator() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getDroppedListValues() {
        return droppedListValues;
    }
}
