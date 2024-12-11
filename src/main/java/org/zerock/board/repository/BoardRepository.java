package org.zerock.board.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{

	 @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
	 Optional<Board> getBoardWithWriter(@Param("bno") Long bno);

	 @Query("select b, w from Board b left join b.writer w")
	 Page<Object[]> getBoardWithWriter(Pageable pageable);

}
