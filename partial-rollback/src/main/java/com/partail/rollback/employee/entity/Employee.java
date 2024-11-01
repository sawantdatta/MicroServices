package com.partail.rollback.employee.entity;

import java.io.Serializable;

import javax.persistence.Column;

@javax.persistence.Entity
@javax.persistence.Table(name ="employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 6200887386545833971L;
	
	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private Integer id;
	private String first_name;
	private String last_name;
	
	@Column(name = "email_id")
	private String email_id;
	
	private Integer department_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public Integer getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}
}