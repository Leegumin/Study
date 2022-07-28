package com.jpa.springboot.dto;

public class MyBag {

	private String id;
	private String name;
	private String email;
	private Integer age;  // Primitive int 기본값은 0. null 값을 가질 수 없음. null 값을 가지려면 Integer로 선언.
	private String gender;
	
	// Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	// toString()
	@Override
	public String toString() {
		return "MyBag [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + ", gender=" + gender + "]";
	}
	
}
