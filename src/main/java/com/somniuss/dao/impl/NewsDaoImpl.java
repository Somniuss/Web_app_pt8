package com.somniuss.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.somniuss.bean.News;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.NewsDao;
import com.somniuss.dao.connectionpoolprovider.ConnectionPoolProvider;

public class NewsDaoImpl implements NewsDao {
	
	private static final String SelectFromNews = ("SELECT * FROM news;");

    @Override
    public List<News> getAllNews() throws DaoException {
        List<News> newsList = new ArrayList<>();
        
        try (Connection connection = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SelectFromNews);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                News news = new News();
                news.setTitle(resultSet.getString("title"));
                news.setContent(resultSet.getString("content"));
                newsList.add(news);
            }
        } catch (Exception e) {
            throw new DaoException("Ошибка при получении новостей", e);
        }
        
        return newsList;
    }
}
