package com.mao.whiteserver.service.impl;

import com.mao.whiteserver.dao.BookDAO;
import com.mao.whiteserver.entity.Book;
import com.mao.whiteserver.entity.Category;
import com.mao.whiteserver.service.BookService;
import com.mao.whiteserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    @Override
    public List<Book> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);
    }

    @Override
    public void addOrUpdate(Book book) {
        Category category = categoryService.get(book.getId());
        book.setCategory(category);
        bookDAO.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    @Override
    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }

    @Override
    public List<Book> Search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }
}
