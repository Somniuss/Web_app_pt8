package com.somniuss.dao;

import com.somniuss.bean.Soundeffects;

public interface SoundeffectsDao {

    // Добавление нового саундэффекта
    boolean add(Soundeffects soundeffect) throws DaoException;

    // Обновление существующего саундэффекта
    boolean update(Soundeffects soundeffect) throws DaoException;
}
