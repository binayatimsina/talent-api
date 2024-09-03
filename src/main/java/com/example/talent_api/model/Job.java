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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ManyToOne
    @JoinColumn(name="manager_id") //Using Entity reference here will take care of the foreign key constarints
    private Manager manager;

    @Column(length = 25)
    private String department;

    @Column(length = 100)
    private String listing_title;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date_listed;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date_closed;

    @Column(length = 45)
    private String job_title;

    private String job_description;
    private String additional_information;

    @Column(length = 25)
    private String listing_status;

    public Job(){

    }

    public Job(Manager manager, String department, String listing_title, String job_title, String job_description, String additional_information, 
            String listing_status) {
        this.manager = manager;
        this.department = department;
        this.listing_title = listing_title;
        this.job_title = job_title;
        this.job_description = job_description;
        this.additional_information = additional_information;
        this.listing_status = listing_status;
    }

    @PrePersist
    protected void onCreate(){
        date_listed = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        date_closed = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getListing_title() {
        return listing_title;
    }

    public void setListing_title(String listing_title) {
        this.listing_title = listing_title;
    }

    public LocalDateTime getDate_listed() {
        return date_listed;
    }

    public void setDate_listed(LocalDateTime date_listed) {
        this.date_listed = date_listed;
    }

    public LocalDateTime getDate_closed() {
        return date_closed;
    }

    public void setDate_closed(LocalDateTime date_closed) {
        this.date_closed = date_closed;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    public String getListing_status() {
        return listing_status;
    }

    public void setListing_status(String listing_status) {
        this.listing_status = listing_status;
    }

    

    
}
