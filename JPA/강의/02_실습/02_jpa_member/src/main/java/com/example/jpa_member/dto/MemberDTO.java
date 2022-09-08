package com.example.jpa_member.dto;

import com.example.jpa_member.entity.Member;
import lombok.Data;

/**
 * packageName : com.example.jpa_member.dto
 * fileName : MemberDTO
 * author : Mingu
 * date : 2022-08-19
 * description : controller에서 입력된 데이터를 받아옴. entity 클래스에 정보 전송
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-19         Mingu          최초 생성
 */
@Data
public class MemberDTO {
    private int    num;
    private String name;
    private String id;
    private String phone;
    private int age;

    // toString
    @Override
	public String toString() {
		return "MemberDTO [num=" + num + ", name=" + name + ", id=" + id + ", phone=" + phone + ", age=" + age + "]";
	}

    // toEntity
    public Member toEntity() {
        return new Member(num, name, id, phone, age);
    }
}
