package com.example.jpa_member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa_member.entity.Member;

/**
 * packageName : com.example.jpa_member.repository fileName : MemberRepository
 * author : Mingu date : 2022-08-22 description : CRUDRepository 또는
 * JpaRepository를 상속받는 인터페이스 CRUD를 실행하기 위해 만들어짐
 * =========================================================== DATE AUTHOR NOTE
 * ----------------------------------------------------------- 2022-08-22 Mingu
 * 최초 생성
 */
// *JpaRepository<형태, ID타입>
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 본 메서드는 전부 페이징이 적용되기 때문에 List<Member>이 아닌 Page<Member>로 적용함

	// 검색 조회 메서드 정의 방법
	// findBy검색내용Containing = 검색내용 포함 검색
	// findBy검색내용= 검색내용 정확 검색

	// 회원 이름으로 검색하는 메서드 정의
	// *findByNameContains도 동일하게 작동
	Page<Member> findByNameContaining(String name, Pageable pageable);

	// 회원 아이디로 검색하는 메서드 정의
	Page<Member> findByIdContaining(String id, Pageable pageable);

	// 회원 번호로 검색하는 메서드 정의
	Page<Member> findByPhoneContaining(String phone, Pageable pageable);

	// 회원 번호로 검색하는 메서드 정의(정확 검색)
	Page<Member> findByPhone(String phone, Pageable pageable);

	// *Like - 특정 문자열이 포함 되었는지? --> 와일드카드(%)를 사용
	// *%내용% -> Containing, 내용% -> 앞에서 검색, %내용 -> 뒤에서 검색
	Page<Member> findByPhoneLike(String phone, Pageable pageable);

	// *위와 동일한 기능의 메서드 (내용%)
	Page<Member> findByPhoneStartsWith(String phone, Pageable pageable);

	// *(%내용)
	Page<Member> findByPhoneEndsWith(String phone, Pageable pageable);

	// and, or 조건 검색
	// *and
	Page<Member> findByNameAndId(String name, String id, Pageable pageable);

	// *or : and와 개념이 좀 다름. 검색 키워드를 name, id 둘 다 받는 것이 아니라 하나만 받아서 하나의 키워드로 양쪽 컬럼을 전부 뒤지는 방식
	// *contains을 조합해서 사용하면 검색 키워드를 포함한(ex - 제목 혹은 내용 검색) 검색을 실시함
	Page<Member> findByNameContainsOrIdContains(String name, String id, Pageable pageable);

	Page<Member> findByNameOrId(String name, String id, Pageable pageable);

	// 조건, 정렬 검색1
	// GreaterThanEqual : Num보다 크거나 같거나 (Num 이상), OrderBy~Asc : Num을 기준으로 정렬(Asc : 오름차순, 생략가능(정렬 디폴트 값이 Asc)
	Page<Member> findByNumGreaterThanEqualOrderByNameAsc(Integer num, Pageable pageable);

	// GreaterLessEqual : Num보다 작거나 같거나 (Num 이상), OrderBy~Asc : Num을 기준으로 정렬(Asc : 오름차순)
	Page<Member> findByAgeLessThanEqualOrderByNameDesc(Integer age, Pageable pageable);

	// 조건, 정렬 검색2
	Page<Member> findByNameContainsOrderByName(String name, Pageable pageable);

	// PageRequest.of() : Page 타입을 사용하여 페이징 구현 시 여러 기능 제공
	// *기본적인 4가지 파라미터 값 : 보여줄 페이지, 페이지당 출력 개수, 정렬 방향, 적용 컬럼명   
	Page<Member> findByNameContains(String name, Pageable pageable);

	// @Query 어노테이션을 이용한 쿼리 작성법
	// *보다 구체적이고 좀 더 직관적이면서 빠르고, 유연한 쿼리를 작성하고자 할 때 사용함 -> 미니 SQL(JPQL) 혹은 순수 SQL(테이블 대상)을 사용
	// *JPQL(Java Persistence Query Language, 엔티티 대상) : 엔티티 객체를 대상으로 검색하는 객체지향 쿼리, 우선순위는 쿼리메서드보다 빠름
	// *기본 사용법 : @Query( JPQL 쿼리 )
	// *회원 이름으로 검색하기 : 'm'은 Member as m 즉 약어로 지정해준 것, ?1은 메서드의 첫 번째의 파라미터 값을 의미(?1 = String name = searchKeyword)
	/* @Query("SELECT m FROM Member m WHERE m.name = ?1 and m.age > 10") */
	/*
	 * @Query("SELECT m FROM Member m WHERE m.name like %?1% and m.age > 10 ORDER BY m.name DESC"
	 * )
	 */
	@Query("SELECT m FROM Member m WHERE (m.name like %?1%) and (m.age between 0 and 10) ORDER BY m.name DESC")
	Page<Member> findByName(String name, Pageable pageable);
	// * 쿼리메서드로 작성시 =>
	Page<Member> findByNameContainsAndAgeBetweenOrderByNameDesc(String name, int a, int b, Pageable pageable);
}
