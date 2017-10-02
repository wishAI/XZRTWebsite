package com.wishai.xzrtw.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "applicant")
public class Applicant {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String grade;
    private String gender;
    private String branch;
    private String experience;
    private String spareTime;

    public Applicant(
            @JsonProperty("name") String name,
            @JsonProperty("grade") String grade,
            @JsonProperty("gender") String gender,
            @JsonProperty("branch") String branch,
            @JsonProperty("experience") String experience,
            @JsonProperty("spareTime") String spareTime
    ) {
        this.name = name;
        this.grade = grade;
        this.gender = gender;
        this.branch = branch;
        this.experience = experience;
        this.spareTime = spareTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpareTime() {
        return spareTime;
    }

    public void setSpareTime(String spareTime) {
        this.spareTime = spareTime;
    }
}
