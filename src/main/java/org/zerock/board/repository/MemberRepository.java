package org.zerock.board.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.board.entity.Member;

//Window - show view - other - gradle
//Spring Data JPA와 Querydsl을 사용하여 동적인 검색 조건을 처리
//Repository에 선언된 메서드들을 통해 동적인 쿼리를 작성하고 실행
public interface MemberRepository 
extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {
 
	Optional<Member> findByEmail(String email);
}
