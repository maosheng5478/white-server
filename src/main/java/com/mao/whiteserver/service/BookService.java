package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> list();

    void addOrUpdate(Book book);

    void deleteById(int id);

    List<Book> listByCategory(int cid);

    List<Book> Search(String keywords);
}
