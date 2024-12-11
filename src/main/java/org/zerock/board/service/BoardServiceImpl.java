package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepository repository;
    
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        repository.save(board);
        return board.getBno();
    }
    
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Function<Object[], BoardDTO> fn = (en -> entityToDTO(
                (Board)en[0], 
                (Member)en[1],
                0L      // 댓글 수는 0으로 처리
        ));
                
        Page<Object[]> result = repository.getBoardWithWriter(
                requestDTO.getPageable(Sort.by("bno").descending()));
                
        return new PageResultDTO<>(result, fn);
    }
    
    @Override
    public BoardDTO get(Long bno) {
        Board board = repository.findById(bno).orElseThrow();
        Member member = board.getWriter();
        
        return entityToDTO(board, member, 0L);
    }
    
    @Override
    public void modify(BoardDTO dto) {
        // 업데이트할 게시물 조회
        Board board = repository.findById(dto.getBno()).orElseThrow();
        
        // 제목, 내용만 수정 가능하도록 처리
        board.changeTitle(dto.getTitle());
        board.changeContent(dto.getContent());
        
        repository.save(board);
    }
    
    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        repository.deleteById(bno);
    }
}
