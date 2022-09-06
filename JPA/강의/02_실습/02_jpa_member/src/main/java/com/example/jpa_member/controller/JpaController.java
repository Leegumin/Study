package com.example.jpa_member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jpa_member.dto.MemberDTO;
import com.example.jpa_member.entity.Member;
import com.example.jpa_member.repository.MemberRepository;

/**
 * packageName : com.example.controller fileName : JpaController author : Mingu
 * date : 2022-08-19 description :
 * =========================================================== DATE AUTHOR NOTE
 * ----------------------------------------------------------- 2022-08-19 Mingu
 * 최초 생성
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
	public String memberRegistrationForm(Model model, @RequestParam(value = "num", required = false) Integer num) {
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
		} catch (Exception e) {
			logger.info("insertMemberError : ", e);
		}

		// *redirect:/ : 메인으로 다시 보냄
		return "redirect:/";
	}

	// 회원 전체 조회 (READ)
	// *defaultValue : 값이 들어오지 않았을 때 기본적으로 주어지는 값
	// *@PageableDefault(출력 개수, 정렬 기준, 정렬 방향) : 페이징 기본 값 부여하는 방법(messages.properties 값을 가져와서 지정해 주지 않는 방법)
	@RequestMapping(value = "/jpa/memberList", method = RequestMethod.GET)
	public String memberList(Model model,
			@PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
			@RequestParam(value = "searchCategory", required = false, defaultValue = "") String searchCategory) {

		System.out.println("--------------------------------------");
		System.out.println("searchCategory = " + searchCategory);
		System.out.println("searchKeyword = " + searchKeyword);
		System.out.println("--------------------------------------");

		// 페이징 처리가 없을 때 회원 전체 조회
		// *멤버 데이터를 모두 찾아서 배열 형태로 정의
		// *JPA 방식이 아닌 경우의 처리
		// *List<MemberDTO> memberList = memberService.getMemberList();
		/* List<Member> members = memberRepository.findAll(); */

		// 페이징 처리가 있을 때 회원 전체 조회
		// *페이징 관련해서 필요한 기능들 -> domain.Page, domain.Pageable, domain.Sort(정렬 관련)
		// *size : 한 페이지당 노출 개수, page : 총 페이지 개수, sort : 정렬 옵션 등
		// *페이지 번호는 0부터 시작함
		// *처음 페이징 적용 시 size default 값은 20개
		// *뷰 페이지단에서 적용할 값을 messages.properties 파일에 정의하고 사용할 수 있음

		// 전체 조회를 할 때 사용하는 메서드
		/* Page<Member> members = memberRepository.findAll(pageable); */
		// => 검색 조회를 하기 위해 변경한 메서드
		/*
		 * Page<Member> members = memberRepository.findByNameContaining(searchKeyword,
		 * pageable);
		 */
		// => 카테고리 검색 조회를 하기 위해 변경한 메서드
		Page<Member> members = null;
		// 검색 카테고리별로 비교하여 해당하는 검색 메서드 호출
		// 이름 검색 : 검색 조건이 이름일 때
		if (searchCategory.equals("name")) {
			System.out.println("이름 검색");
			members = memberRepository.findByNameContaining(searchKeyword, pageable);
		}
		// 아이디 검색 : 검색 조건이 아이디일 때
		else if (searchCategory.equals("id")) {
			System.out.println("아이디 검색");
			members = memberRepository.findByIdContaining(searchKeyword, pageable);
		}
		// 전화번호 검색 : 검색 조건이 전화번호일 때
		else if (searchCategory.equals("phone")) {
			System.out.println("휴대폰 검색");
			/* members = memberRepository.findByPhoneContaining(searchKeyword, pageable); */
			
			// Like 검색1
			/* members = memberRepository.findByPhoneLike(searchKeyword+"%", pageable); */
			
			// Like 검색2 : 와일드카드를 사용하지 않고 메서드 자체에서 이미 처리가 가능
			members = memberRepository.findByPhoneStartsWith(searchKeyword, pageable);
		}
		// 전체 검색 : 검색 조건이 없을 때
		else {
			System.out.println("전체 검색");
			/* members = memberRepository.findAll(pageable); */
			
			// and, or 조건 검색
			// *and
			/*
			 * members = memberRepository.findByNameAndId(searchCategory, searchKeyword,
			 * pageable);
			 */
			
			// *or
			members = memberRepository.findByNameContainsOrIdContains(searchKeyword, searchKeyword, pageable);
		}

		// 페이징 관련 유용한 메서드
		// *getTotalPages() : 총 페이지 수
		System.out.println("getTotalPages = " + members.getTotalPages());
		// *getTotalElements() : 총 데이터 개수
		System.out.println("getTotalElements = " + members.getTotalElements());
		// *getNumber() : 현재 페이지 번호
		System.out.println("getNumber = " + members.getNumber());
		// *getSize() : 한 페이지 보여지는 데이터 개수
		System.out.println("getSize = " + members.getSize());
		// *getSort() : 데이터 정렬
		System.out.println("getSort = " + members.getSort());
		// *getPageable() : size, number, sort 3가지 값을 보여줌
		System.out.println("getSort = " + members.getPageable());

		model.addAttribute("members", members);

		return "jpa/memberList";
	}

	// 회원 상세 조회 (READ)
	@RequestMapping(value = "/jpa/memberDetail", method = RequestMethod.GET)
	public String memberDetail(Model model, @RequestParam(value = "num", required = false) Integer num) {
		logger.info("num : {}", num);

		// *num(기본 키 값)에 해당하는 회원 조회
		Member member = memberRepository.findById(num).orElse(null);
		model.addAttribute("member", member);

		return "jpa/memberDetail";
	}

	// 회원 삭제 (DELETE)
	@RequestMapping(value = "/jpa/memberDelete", method = RequestMethod.GET)
	public String memberDelete(@RequestParam(value = "num", required = false) Integer num) {
		logger.info("num : {}", num);
		// *키 값(num)에 따라 데이터 삭제
		memberRepository.deleteById(num);
		return "redirect:/";
	}
}
