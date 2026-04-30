package com.example.front.model;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class CourseForm {
	private String courseName;
	private BigDecimal price;
	private Integer countryId;
	private MultipartFile fileData;

	public MultipartFile getFileDate()  {return fileData;}
	public void setFileDate(MultipartFile fileDate) {this.fileData = fileDate;}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}


}
