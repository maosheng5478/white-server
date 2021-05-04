package com.mao.whiteserver.service;

import com.mao.whiteserver.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    Category get(int id);
}