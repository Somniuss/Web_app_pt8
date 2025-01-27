package com.somniuss.service;

import com.somniuss.bean.Soundeffects;

public interface SoundeffectsService {
    
    // Метод для добавления нового саундэффекта
    boolean add(Soundeffects soundeffect) throws ServiceException;
}
