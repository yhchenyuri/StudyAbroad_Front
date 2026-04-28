package com.example.front.model;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointments {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(nullable = false, unique = true, length = 36)
	    private String uuid;

	    private String name;
	    private String phone;
	    private String email;
	    
	    @Column(name = "country_id")
	    private Integer countryId;

	    @Column(columnDefinition = "TEXT")
	    private String requirement;
	    
	    @Column(name = "created_at", insertable = false, updatable = false)
	    private Timestamp createdAt;

	}

