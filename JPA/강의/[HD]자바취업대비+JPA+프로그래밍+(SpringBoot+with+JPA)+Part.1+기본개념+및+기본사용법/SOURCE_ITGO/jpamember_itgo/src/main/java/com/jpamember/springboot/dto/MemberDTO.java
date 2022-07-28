package com.jpamember.springboot.dto;

import com.jpamember.springboot.entity.Member;

public class MemberDTO {

	// Fields
	private int num;
	private String name;
	private String id;
	private String phone;
	private int age;
	
	// Getters and Setters
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	// toString()
	@Override
	public String toString() {
		
		return "MemberDTO [num=" + num + ", name=" + name + ", id=" + id + ", phone=" + phone + "]";
	}
	
	// toEntity()
	public Member toEntity() {
		
		return new Member( num, name, id, phone, age ); 
	}
	
	
}







