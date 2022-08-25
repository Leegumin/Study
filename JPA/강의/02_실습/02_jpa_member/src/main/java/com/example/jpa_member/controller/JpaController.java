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

import java.util.List;


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

    // *save : CREATE, UPDATE에 사용됨
    // *findAll : READ에 사용됨


    // 등록 처리(신규회원), 수정처리(기존회원)
    // *폼
    @RequestMapping(value = "/jpa/memberRegistration", method = RequestMethod.GET)
    public String memberRegistrationForm(Model model,
                                         @RequestParam(value = "num", required = false)
                                         Integer num) {
        logger.info("memberRegistrationForm가 실행됨");
        // 기존 회원 수정 ( 기본 키 값(num)이 있음 )
        if (num != null) {
            System.out.println(num);
            // *findById(num) : 매개변수(기본 키) 값을 통해 매칭되는 정보를 DB에서 가져옴;
            Member member = memberRepository.findById(num).orElse(null);
            // *memberDTO라는 이름으로 넘겨주기는 하지만 실상 entity 클래스
            model.addAttribute("memberDTO", member);
            model.addAttribute("formTitle", "Modification");
            logger.info("member : {} ", member);

        }
        // 신규 회원 등록 ( 기본 키 값(num)이 없음 )
        else {
            System.out.println("미등록된 회원입니다.");
            // *신규회원 등록이기 때문에 새로운 memberDTO 빈객체를 넘겨줌
            model.addAttribute("memberDTO", new MemberDTO());
            model.addAttribute("formTitle", "Registration");
            logger.info("member : {} ", new MemberDTO());
        }

        return "jpa/memberRegistration";
    }

    // 등록처리 (CREATE), 수정처리 (UPDATE)
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
            // *save 메서드의 경우 기본 키 값이 없을 경우 등록(CREATE), 있을 경우 수정(UPDATE)을 자동으로 처리 => 뷰페이지단에서 반드시 기본 키 값이 넘어와야한다는 소리임
            memberRepository.save(member);
        }
        catch (Exception e) {
            logger.info("insertMemberError : ", e);
        }

        // *redirect:/ : 메인으로 다시 보냄
        return "redirect:/";
    }

    // 회원 조회 (READ)
    @RequestMapping(value = "/jpa/memberList", method = RequestMethod.GET)
    public String memberList(Model model) {

        // *멤버 데이터를 모두 찾아서 배열 형태로 정의
        // *JPA 방식이 아닌 경우의 처리
        // *List<MemberDTO> memberList = memberService.getMemberList();
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);

        return "jpa/memberList";
    }

    // 회원 삭제 (DELETE)
    @RequestMapping(value = "/jpa/memberDelete", method = RequestMethod.GET)
    public String memberDelete(
            @RequestParam(value = "num", required = false)
            Integer num) {
        logger.info("num : {}", num);
        // *키 값(num)에 따라 데이터 삭제
        memberRepository.deleteById(num);
        return "redirect:/";
    }
}
