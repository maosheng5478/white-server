package com.mao.whiteserver.dao;

import com.mao.whiteserver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
