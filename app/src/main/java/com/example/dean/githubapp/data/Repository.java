package com.example.dean.githubapp.data;

import java.io.Serializable;

public class Repository implements Serializable {

    private String name;
    private String owner;
    private String avatarUrl;
    private int watchers;
    private int forks;
    private int issues;
    private String language;
    private String description;
    private String datePublished;
    private String dateModified;

    public Repository(String name, String owner, String avatarUrl, int watchers, int forks,
                      int issues, String language, String description, String datePublished,
                      String dateModified) {
        this.name = name;
        this.owner = owner;
        this.avatarUrl = avatarUrl;
        this.watchers = watchers;
        this.forks = forks;
        this.issues = issues;
        this.language = language;
        this.description = description;
        this.datePublished = datePublished;
        this.dateModified = dateModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
