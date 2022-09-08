package com.example.jpa_member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * packageName : com.example.jpa_member.entity fileName : Member author : Mingu
 * date : 2022-08-22 description : 데이터베이스 테이블과 직접적으로 연관되는 클래스
 * =========================================================== DATE AUTHOR NOTE
 * ----------------------------------------------------------- 2022-08-22 Mingu
 * 최초 생성
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	// *@Id : Entity의 기본 키라는걸 알려주는 어노테이션
	// *@GeneratedValue(strategy = GenerationType.IDENTITY) : 주키의 값을 위한 자동 생성 전략을
	// 명시하는데 사용
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;
	// *@Column : Entity의 컬럼 값이라는걸 알려주는 어노테이션
	@Column
	private String name;
	@Column
	private String id;
	@Column
	private String phone;
	@Column
	private int age;

	/*
	 * // *롬북 어노테이션으로 대체 // *기본 생성자 => @NoArgsConstructor public Member() { }
	 * 
	 * // *전체 생성자 => @AllArgsConstructor
	 */

	/*
	 * public Member(int num, String name, String id, String phone) { this.num =
	 * num; this.name = name; this.id = id; this.phone = phone; }
	 */

}
