package org.zerock.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.MemberRepository;
import org.zerock.board.service.BoardService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = Chap13Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Chap13ApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BoardService boardService;

	private static Long savedBno;

	@Test
	@Order(1)
	void contextLoads() {
		// 스프링 컨텍스트가 제대로 로드되는지 테스트
		assertNotNull(boardRepository);
		assertNotNull(memberRepository);
		assertNotNull(boardService);
	}

	@Test
	@Order(2)
	void testInsertBoard() {
		// 게시글 등록 테스트
		Member member = Member.builder()
				.email("user1@aaa.com")  // 이미 존재하는 사용자 이메일 사용
				.pwd("1111")
				.name("USER1")
				.build();

		Board board = Board.builder()
				.title("테스트 제목")
				.content("테스트 내용")
				.writer(member)
				.build();

		boardRepository.save(board);
		savedBno = board.getBno();
		System.out.println("생성된 게시글 번호: " + savedBno);
		assertNotNull(board.getBno());
	}

	@Test
	@Order(3)
	@Transactional
	void testReadBoard() {
		// 게시글 조회 테스트
		assertNotNull(savedBno, "게시글이 먼저 생성되어야 합니다");
		Board board = boardRepository.findById(savedBno).orElseThrow();
		
		assertEquals("테스트 제목", board.getTitle());
		assertNotNull(board.getWriter());
	}

	@Test
	void testBoardList() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.getBoardWithWriter(pageable);
		
		result.get().forEach(arr -> {
			System.out.println("Board: " + arr[0]);
			System.out.println("Member: " + arr[1]);
		});
		
		assertNotNull(result);
	}

	@Test
	void testBoardService() {
		// 서비스 계층 테스트
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.page(1)
				.size(10)
				.build();

		var result = boardService.getList(pageRequestDTO);
		
		assertNotNull(result);
		assertTrue(result.getDtoList().size() > 0);
	}

	@Test
	@Order(4)
	@Transactional
	void testModifyBoard() {
		// 게시글 수정 테스트
		BoardDTO dto = BoardDTO.builder()
				.bno(savedBno)
				.title("수정된 제목")
				.content("수정된 내용")
				.build();

		boardService.modify(dto);
		
		BoardDTO modified = boardService.get(savedBno);
		assertEquals("수정된 제목", modified.getTitle());
	}

	@Test
	@Order(5)
	void testRemoveBoard() {
		// 게시글 삭제 테스트
		boardService.removeWithReplies(savedBno);
		
		assertThrows(Exception.class, () -> {
			boardService.get(savedBno);
		});
	}

	@Test
	void testSearch() {
		// 게시글 검색 테스트
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.getBoardWithWriter(pageable);
		
		result.get().forEach(arr -> {
			System.out.println(arr[0]); // Board
			System.out.println(arr[1]); // Member
		});
	}
}
