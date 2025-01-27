package com.somniuss.service;

import java.util.List;

import com.somniuss.bean.News;

public interface NewsService {
    List<News> getAllNews() throws ServiceException;
}
