package com.practice.employee.dto;

import java.io.Serializable;

public class DepartmentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012608127908042294L;
	private Integer id;
	private String departmentName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}