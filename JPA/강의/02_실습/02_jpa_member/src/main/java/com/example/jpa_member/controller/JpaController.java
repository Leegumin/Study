package com.example.jpa_member.controller;

import com.example.jpa_member.dto.MemberDTO;
import com.example.jpa_member.entity.Member;
import com.example.jpa_member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // DI 주입
    @Autowired
    private MemberRepository memberRepository;

    // 등록 처리(신규회원)
    // *등록 폼
    @RequestMapping(value = "/jpa/memberRegistration", method = RequestMethod.GET)
    public String memberRegistrationForm(Model model,
                                     @RequestParam(value = "num", required = false)
                                     Integer num) {
        logger.info("memberRegistrationForm가 실행됨");
        // 기존 회원 수정 ( 기본 키 값(num)이 있음 )
        if (num != null) {
            System.out.println(num);
        }
        // 신규 회원 등록 ( 기본 키 값(num)이 없음 )
        else {
            System.out.println("미등록된 회원입니다.");
            model.addAttribute("memberDTO", new MemberDTO());
            model.addAttribute("formTitle", "Registration");
        }
        return "jpa/memberRegistration";
    }

    // 등록 처리 (CREATE)
    @RequestMapping(value = "/jpa/memberRegistration", method = RequestMethod.POST)
    public String memberRegistration(Model model, MemberDTO memberDTO) {
        logger.info("memberRegistration가 실행됨");
        try {
            logger.info("memberDTO : {}", memberDTO);

            // 1. DTO를 Entity로 변환
            Member member = memberDTO.toEntity();
            logger.info("member : {}", member);

            // 2. Repository로 Entity를 DB에 저장
            // *memberDTO로 받은 정보 => Entity Member로 변환 => MemberRepository로 저장
            memberRepository.save(member);
        }
        catch (Exception e) {
            logger.info("insertMemberError : ", e);
        }

        // *redirect:/ : 메인으로 다시 보냄
        return "redirect:/";
    }

    // 회원 조회
    @RequestMapping(value = "/jpa/memberList", method = RequestMethod.GET)
    public String memberList(Model model) {
        return "jpa/memberList";
    }
}
