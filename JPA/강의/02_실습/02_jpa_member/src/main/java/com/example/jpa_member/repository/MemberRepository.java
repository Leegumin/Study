package com.example.jpa_member.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	// JPQL의 다양한 사용법

	// 1.@Query 애너테이션 + JPQL을 사용한 수정 처리
	// *기본적으로 쿼리 애너테이션을 이용한 수정 처리시에는 추가시켜주는 애너테이션들이 있음
	// * ? --> 순서 기반, : --> 이름 기반 (명시적인 매개변수명 일치시켜 사용)
	// *@Modifying : 수정을 위한 어노테이션
	// *@Transactional : 연산이 고립되어, 다른 연산과의 혼선으로 인해 잘못된 값을 가져오는 경우가 방지된다. 연산의 원자성이 보장되어, 연산이 도중에 실패할 경우 변경사항이 Commit되지 않는다.
	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.name = :name, m.age = :age WHERE m.num = :num")
	int updateMemberQuery(@Param("num") int num, @Param("name") String name, @Param("age") int age);

	// 2.@Query 애너테이션 + JPQL을 사용한 LIKE 콜론(:) 파라미터 바인딩
	// *변수 이름만 일치하면 메소드 정의에서 변수 순서를 맞춰줄 필요는 없음
	@Query("SELECT m FROM Member m WHERE (m.name LIKE %:name%) AND (m.age BETWEEN :from AND :to) ORDER BY m.name ASC")
	Page<Member> findMembers(@Param("name") String name, @Param("from") int from, @Param("to") int to,
			Pageable pageable);

	// EXISTS, COUNT 쿼리 메서드, 순수 SQL 사용
	// *EXISTS : 존재여부만을 체크함, 보통 두 개의 테이블에서 같은 값을 가진 컬럼끼리 묶어서 -> 조건비교 -> 서브쿼리 형태로 사용됨. 한 개의 테이블에서도 서브쿼리로 사용
	// *EXISTS 서브쿼리는 서브쿼리의 결과가 "한 건이라도 존재한다면" -> TRUE, 반대면 FALSE로 반환
	// *서브쿼리의 조건에 일치하는 레코드가 한 건 이라도 있으면(TRUE라면) 즉시 쿼리를 멈추고 메인쿼리를 실행시킴
	// *사용예시 : SELECT * FROM Member m WHERE (m.name LIKE '%순신%') AND EXISTS (SELECT 1 FROM Member mm.WHERE mm.age > 20);
	// * 존재여부를 체크하는 방법
	// *1. 쿼리 메서드 --> existsByName[Id], 2. @Query 애너테이션 + JPQL 쿼리 사용 --> JPQL은 순수 쿼리가 아니라 exists 사용 불가능
	// *3. 대신 COUNT 쿼리를 사용하여 조회함
	// *존재여부만 체크하기 때문에 boolean 타입을 사용함
	boolean existsByName(String name);

	// *COUNT : 개수를 체크함
	// *이런식으로 처리 가능함. 공백 주의
	@Query("SELECT count(m.num) "
			+ "FROM Member m "
			+ "WHERE m.name = :name AND m.id = :id")
	int existsQuery(@Param(value = "name") String name, @Param(value="id") String id);
	
	// 3.일반 순수 SQL 쿼리
	// JPA의 사용 목적과는 조금은 반대되는 부분일 수 있으므로 많이 권장하지는 않는 편임
	// 그러나, 특정 데이터베이스의 함수나 기능을 사용하여 성능향상을 꾀하든지 또는 복잡한 쿼리나 지원하지 않는 경우 사용이 가능
	// ; --> 세미클론 사용은 X
	// @Query 애터네이션 + SQL --> value = "" --> 옵션 --> nativeQuery = true
	@Query(value = "SELECT * FROM member m WHERE m.name LIKE %:searchKeyword% AND EXISTS (SELECT 1 FROM member mm WHERE mm.age < 10)", nativeQuery = true)
	Page<Member> selectAllSQL(Pageable pageable, @Param("searchKeyword") String searchKeyword);
}
