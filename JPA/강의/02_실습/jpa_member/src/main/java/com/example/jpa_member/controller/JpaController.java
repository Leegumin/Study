package com.example.jpa_member.controller;

import com.example.jpa_member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * packageName : com.example.controller
 * fileName : JpaController
 * author : Mingu
 * date : 2022-08-19
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-19         Mingu          최초 생성
 */
@Controller
public class JpaController {

    // 등록 처리(신규회원)
    // *등록 폼
    @RequestMapping(value = "/jpa/memberRegistration", method = RequestMethod.GET)
    public String memberRegistration(Model model) {
        return "jpa/memberRegistration";
    }

    // *등록 처리
    @RequestMapping(value = "/jpa/memberRegistrationOk", method = RequestMethod.POST)
    public String insertMember(Model model, MemberDTO memberDTO) {
        try {

        }
        catch (Exception e) {

        }

        return "jpa/memberRegistration";
    }

    // 회원 조회
    @RequestMapping(value = "/jpa/memberList", method = RequestMethod.GET)
    public String memberList(Model model) {
        return "jpa/memberList";
    }
}
