package com.partail.rollback.department.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


@javax.persistence.Entity
@javax.persistence.Table(name = "department")
public class Department implements Serializable {

	private static final long serialVersionUID = 6200887386545833971L;


	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private Integer dept_id;

	private String dept_name;

	private Long salary;

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	
}