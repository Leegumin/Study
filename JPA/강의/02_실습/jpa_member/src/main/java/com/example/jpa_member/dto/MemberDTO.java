package com.example.jpa_member.dto;

import lombok.Data;

/**
 * packageName : com.example.jpa_member.dto
 * fileName : MemberDTO
 * author : Mingu
 * date : 2022-08-19
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-19         Mingu          최초 생성
 */
@Data
public class MemberDTO {
    private String name;
    private String id;
    private String phone;
}
