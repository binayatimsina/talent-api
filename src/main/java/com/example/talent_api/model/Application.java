package com.example.talent_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ManyToOne
    @JoinColumn(name="user_id") //Using Entity reference here will take care of the foreign key constarints
    private User user;

    @ManyToOne
    @JoinColumn(name="job_id") //Using Entity reference here will take care of the foreign key constarints
    private Job job;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date_applied;

    private String cover_letter;
    private String custom_resume;

    @Column(length = 45)
    private String application_status;

    public Application(){

    }

    public Application(User user, Job job, String cover_letter, String custom_resume,
            String application_status) {
        this.user = user;
        this.job = job;
        this.cover_letter = cover_letter;
        this.custom_resume = custom_resume;
        this.application_status = application_status;
    }

    @PrePersist
    protected void onCreate(){
        date_applied = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public LocalDateTime getDate_applied() {
        return date_applied;
    }

    public void setDate_applied(LocalDateTime date_applied) {
        this.date_applied = date_applied;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    public String getCustom_resume() {
        return custom_resume;
    }

    public void setCustom_resume(String custom_resume) {
        this.custom_resume = custom_resume;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    
    

}
