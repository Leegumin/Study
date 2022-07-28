package com.jpamember.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpamember.springboot.dto.MemberDTO;
import com.jpamember.springboot.entity.Member;
import com.jpamember.springboot.repository.MemberRepository;

@Controller
public class JpaController {
		
	/*
	 * DI
	 * 
	 */
	@Autowired
	private MemberRepository memberRepository;
	
	/*
	 * 회원 등록 Form 페이지 + 회원 수정 Form
	 * 
	 */
	@RequestMapping(value = "/jpa/memberWrite", method = RequestMethod.GET)
	public String memberWriteForm( 
			@RequestParam(value = "num", required = false) Integer num, 
			Model model ) {
		
		if( num != null ) {
			System.out.println( num );
			
			// 기존 회원 수정
			Member member = memberRepository.findById(num).orElse(null);
			model.addAttribute( "memberDTO", member );
			model.addAttribute( "formTitle", "Modification" );
		}
		else {
			System.out.println( "null 이네요..!!" );
			
			// 신규 회원 등록
			model.addAttribute( "memberDTO", new MemberDTO() );
			model.addAttribute( "formTitle", "Registration" );
		}
		
		return "jpa/memberWriteForm";
	}

	/*
	 * 회원 등록 Ok + 회원 수정 Ok
	 * 
	 */
	@RequestMapping(value = "/jpa/memberWrite1", method = RequestMethod.POST)
	public String memberWriteOk1( MemberDTO memberDTO, Model model ) {
		
		try {
			// 등록 처리
			System.out.println( memberDTO.getName() );
			System.out.println( memberDTO.getId() );
			System.out.println( memberDTO.getPhone() );
			System.out.println( memberDTO.toString() );
			
			// 1. DTO 변환 --> Entity
			Member member = memberDTO.toEntity();
			System.out.println( member.toString() );
			
			// 2. Repository로 --> Entity를 --> DB에 저장
			// memberRepository.save( member );
			Member saved = memberRepository.save( member );
			System.out.println( saved.toString() );
			
		}
		catch (Exception e) {
			// err
		}
		
		
		return "redirect:/";
	}
	
	/*
	 * 회원 등록 Ok + 회원 수정 Ok 2 - @Query 애너테이션을 이용하여 수정 처리
	 * 
	 */
	@RequestMapping(value = "/jpa/memberWrite", method = RequestMethod.POST)
	public String memberWriteOk2( 
			MemberDTO memberDTO, 
			Model model,
			@RequestParam(required = false) int num, 
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) int age) {
		
		try {
			// int saved = memberRepository.updateMemberQuery( 1, "테스트777", 77 );
			int saved = memberRepository.updateMemberQuery( num, name, age );
			
			// saved는 수정 처리된 레코드의 갯수를 반환
			System.out.println( "saved = " + saved );
			
		}
		catch (Exception e) {
			// err
		}
		
		return "redirect:/";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 회원 리스트1
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList1", method = RequestMethod.GET)
	public String memberList1( 
			Model model, 
			@PageableDefault(size = 15) Pageable pageable, 
			@RequestParam(value = "searchCate", required = false, defaultValue = "") String searchCate, 
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword ) {

		System.out.println( "--------------------------------------" );
		System.out.println( "searchCate = " + searchCate );
		System.out.println( "serachKeyword = " + searchKeyword );
		System.out.println( "--------------------------------------" );
		
		// JPA 방식이 아닌 경우에는 아래처럼 처리
		// List<MemberDTO> memberList = memberService.getMemberList();
		
		// JPA 방식
		// List<Member> members = memberRepository.findAll();
		
		
		// [1] : 기존 리스트 출력
		// Page<Member> members = memberRepository.findAll( pageable );
		
		// [2] : 검색 리스트 출력
		// Page<Member> members = memberRepository.findByNameContaining( searchKeyword, pageable );
		
		// [3] : 검색 리스트 출력 --> name, id, phone
		Page<Member> members = null;
		
		// 검색 카테고리별로 비교하여 해당하는 검색 메서드 호출 --> 문자열 비교는 == 이 아니라 equals 사용.
		if( searchCate.equals("name") ) {
			System.out.println( "이름 검색" );
			members = memberRepository.findByNameContaining(searchKeyword, pageable);
		}
		else if( searchCate.equals("id") ) {
			System.out.println( "아이디 검색" );
			members = memberRepository.findById(searchKeyword, pageable);
		}
		else if( searchCate.equals("phone") ) {
			System.out.println( "휴대폰 검색" );
			// members = memberRepository.findByPhoneContaining(searchKeyword, pageable);
			// members = memberRepository.findByPhone(searchKeyword, pageable);
			
			// findByPhoneLike
			// members = memberRepository.findByPhoneLike(searchKeyword +"%", pageable);
			
			// findByPhoneStartsWith
			// members = memberRepository.findByPhoneStartsWith(searchKeyword, pageable);
			
			// findByPhoneEndsWith
			// members = memberRepository.findByPhoneEndsWith(searchKeyword, pageable);
			
			// 대소문자 구분
			members = memberRepository.findByIdContains(searchKeyword, pageable);
		}
		else {
			System.out.println( "전체 검색" );
			// members = memberRepository.findAll( pageable );
			
			// and 쿼리 조건
			// searchCate, searchKeyword --> name, id
			// members = memberRepository.findByNameAndId(searchCate, searchKeyword, pageable);
			
			// or 쿼리 조건
			members = memberRepository.findByNameContainsOrIdContains( searchKeyword, searchKeyword, pageable);
		}
	
		// 객체 리스트 전달 - 모델에 담아서 리스트 뷰페이지로 전달
		model.addAttribute( "members", members );

		return "jpa/memberList";
	}
	
	/*
	 * 회원 리스트2 - JPA 정렬 쿼리 메서드 만들기1
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList2", method = RequestMethod.GET)
	public String memberList2( 
			Model model, 
			Pageable pageable, 
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword) {
		
		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			members = memberRepository.findByNumGreaterThanEqualOrderByNameAsc( 3, pageable );
		}
		
		// 객체 리스트 전달 - 모델에 담아서 리스트 뷰페이지로 전달
		model.addAttribute( "members", members );
		
		return "jpa/memberList";
	}

	/*
	 * 회원 리스트3 - JPA 정렬 쿼리 메서드 만들기2
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList3", method = RequestMethod.GET)
	public String memberList3( 
			Model model, 
			Pageable pageable, 
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword) {

		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			members = memberRepository.findByAgeLessThanEqualOrderByNameAsc( 20, pageable );
		}
		
		// 객체 리스트 전달
		model.addAttribute( "members", members );
		
		return "jpa/memberList";
	}

	/*
	 * 회원 리스트4 - JPA 정렬 쿼리 메서드 만들기3
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList4", method = RequestMethod.GET)
	public String memberList4( 
			Model model, 
			Pageable pageable, 
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword) {

		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			
			
			// [1] : 등록순 정렬
			// pageable = PageRequest.of( 2, 3, Sort.Direction.DESC, "num" );  // num 기준으로 정렬 적용.
			
			// [2] : 따로 지정이 없으면 오름차순(Asc) 디폴트 적용 --> 컬럼만 지정
			pageable = PageRequest.of( pageable.getPageNumber(), 3, Sort.by("name") );
			
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			
			// [1] : 검색순 정렬 --> 이름 오름차순으로.
			// pageable = PageRequest.of( pageable.getPageNumber(), 3, Sort.Direction.ASC, "name" );
			
			// [2] : 다중 정렬
			pageable = PageRequest.of( pageable.getPageNumber(), 10, Sort.by("age").descending().and(Sort.by("name").descending()) );
			
			members = memberRepository.findByNameContains( searchKeyword, pageable );
		}
		
		// 객체 리스트 전달
		model.addAttribute( "members", members );
		model.addAttribute( "searchKeyword", searchKeyword );
		
		return "jpa/memberList";
	}

	/*
	 * 회원 리스트5 - @Query Annotation --> ? (순서 기반)
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList5", method = RequestMethod.GET)
	public String memberList5(
			Model model, 
			Pageable pageable, 
			@RequestParam(value = "searchKeyword", required = false, defaultValue = "") String searchKeyword) {
		
		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			// pageable = PageRequest.of( pageable.getPageNumber(), 2, Sort.Direction.DESC, "name" );
			members = memberRepository.findByNameContainingAndAgeBetweenOrderByNameAsc( searchKeyword, 30, 40, pageable );
		}
		
		// 객체 리스트 전달
		model.addAttribute( "members", members );
		model.addAttribute( "searchKeyword", searchKeyword );
		
		return "jpa/memberList";
	}
	
	/*
	 * 회원 리스트6 - @Query 파라미터 바인딩 --> :(콜론)
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList6", method = RequestMethod.GET)
	public String memberList6(
			Model model, 
			Pageable pageable,
			@RequestParam(required = false, defaultValue = "0") int from, 
			@RequestParam(required = false, defaultValue = "0") int to, 
			@RequestParam(required = false, defaultValue = "") String searchKeyword) {
		
		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			members = memberRepository.findMembers( searchKeyword, from, to, pageable );
		}
		
		// 객체 리스트 전달
		model.addAttribute( "members", members );
		model.addAttribute( "searchKeyword", searchKeyword );
		
		return "jpa/memberList";
	}

	/*
	 * 회원 리스트7 - 회원 존재 여부 - existsByName()
	 * 
	 */
	@RequestMapping(value = "/jpa/memberList", method = RequestMethod.GET)
	public String memberList7(
			Model model, 
			Pageable pageable,
			@RequestParam(required = false) String id, 
			@RequestParam(required = false, defaultValue = "") String searchKeyword) {
		
		// 변수 초기화
		Page<Member> members = null;
		
		// 전체출력 or 검색출력
		if( searchKeyword.isEmpty() ) {
			System.out.println( "전체출력" );
			members = memberRepository.findAll(pageable);
		}
		else {
			System.out.println( "검색출력" );
			boolean isMember = memberRepository.existsByName( searchKeyword );
			int isExistCount = memberRepository.existsQuery( searchKeyword, id );
			
			// 존재 여부
			System.out.println( "isMember = " + isMember );
			System.out.println( "isExistCount = " + isExistCount );
			
			// members = memberRepository.findByName( searchKeyword, pageable );
			members = memberRepository.selectAllSQL( searchKeyword, pageable );
		}
		
		// 객체 리스트 전달
		model.addAttribute( "members", members );
		model.addAttribute( "searchKeyword", searchKeyword );
		
		return "jpa/memberList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 회원 삭제 Ok
	 * 
	 */
	@RequestMapping(value = "/jpa/memberDelete", method = RequestMethod.GET)
	public String memberDelete( @RequestParam(value = "num", required = false) Integer num ) {
		
		System.out.println( num );
		memberRepository.deleteById( num );
		
		return "redirect:/";
	}
	
	/*
	 * 회원 상세 페이지
	 * 
	 */	
	@RequestMapping(value = "/jpa/memberDetail", method = RequestMethod.GET)
	public String memberDetail(
			@RequestParam(value = "num", required = false) Integer num, 
			Model model
			) {
		
		// 콘솔에 출력
		System.out.println( num );
		
		// findById로 해당 num에 해당하는 회원을 찾아서 뷰페이지로 전달
		Member member = memberRepository.findById(num).orElse(null);
		model.addAttribute( "member", member );
		
		return "jpa/memberDetail";
	}
	

	
}
