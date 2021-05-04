package com.mao.whiteserver.controller;

import com.mao.whiteserver.entity.Book;
import com.mao.whiteserver.service.impl.BookServiceImpl;
import com.mao.whiteserver.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BookServiceImpl bookServiceImpl;

    @GetMapping("/api/books")
    public List<Book> list() throws Exception {
        logger.debug("load books");
        return bookServiceImpl.list();
    }

    @PostMapping("/api/books")
    public Book addOrUpdate(@RequestBody Book book) throws Exception {
        bookServiceImpl.addOrUpdate(book);
        return book;
    }

    @PostMapping("/api/delete")
    public void delete(@RequestBody Book book) throws Exception {
        logger.debug("delete book");
        bookServiceImpl.deleteById(book.getId());
    }

    @GetMapping("/api/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            return bookServiceImpl.listByCategory(cid);
        } else {
            return list();
        }
    }

    @GetMapping("/api/search")
    public List<Book> searchResult(@RequestParam("keywords") String keywords) {
        // 关键词为空时查询出所有书籍
        if ("".equals(keywords)) {
            return bookServiceImpl.list();
        } else {
            return bookServiceImpl.Search(keywords);
        }
    }

    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) {
        String folder = "D:/myself/IdeaProject/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8081/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
