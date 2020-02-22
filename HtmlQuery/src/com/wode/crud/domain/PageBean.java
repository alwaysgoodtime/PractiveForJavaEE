package com.wode.crud.domain;

import java.util.List;

/**
 * @author goodtime
 * @create 2019-12-30 3:12 下午
 */
public class PageBean<T>{
    private int currentPage;//当前页数
    private int allRows;//数据库总条数
    private int rows;//每页分几条
    private int pages;//总共有几页
    private List<T> list;//封装的查询记录，泛型是为了复用

    public PageBean() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllRows() {
        return allRows;
    }

    public void setAllRows(int allRows) {
        this.allRows = allRows;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

//    方便调试，部署时直接删除
    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", allRows=" + allRows +
                ", rows=" + rows +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }
}

