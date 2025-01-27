package com.somniuss.dao;

import java.util.List;

import com.somniuss.bean.News;

public interface NewsDao {
    List<News> getAllNews() throws DaoException;
}
