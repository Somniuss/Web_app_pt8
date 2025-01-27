package com.somniuss.bean;

import java.io.Serializable;
import java.util.Objects;

public class Soundeffects implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title; // Название саундэффекта
    private String description; // Описание саундэффекта
    private String filePath; // Путь к звуковому файлу
    private double durationInSeconds; // Длительность в секундах

    public Soundeffects() {
    }

    public Soundeffects(String title, String description, String filePath, double durationInSeconds) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.durationInSeconds = durationInSeconds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(double durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, filePath, durationInSeconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Soundeffects other = (Soundeffects) obj;
        return Objects.equals(title, other.title) &&
               Objects.equals(description, other.description) &&
               Objects.equals(filePath, other.filePath) &&
               Double.compare(other.durationInSeconds, durationInSeconds) == 0;
    }

    @Override
    public String toString() {
        return "Soundeffects [title=" + title + ", description=" + description + ", filePath=" + filePath + ", duration=" + durationInSeconds + "]";
    }
}
