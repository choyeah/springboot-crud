package org.zerock.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.service.BoardService;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

   @GetMapping("/list")
   public void list(PageRequestDTO pageRequestDTO, Model model){
	   log.info("list............." + pageRequestDTO);
       model.addAttribute("result", boardService.getList(pageRequestDTO));
   }

    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);
        
        // 새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);
        
        log.info("BNO: " + bno);
        
        // 등록 후 목록 페이지로 이동할 때 메시지 전달
        redirectAttributes.addFlashAttribute("msg", bno + " 등록");
        
        return "redirect:/board/list";  // 등록 후 목록 페이지로 리다이렉트
    }

    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam("bno") Long bno, 
                    @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                    Model model) {
        log.info("bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto, 
                    @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                    RedirectAttributes redirectAttributes) {
        log.info("post modify.........................................");
        log.info("dto: " + dto);
        
        boardService.modify(dto);
        
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());
        
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, 
                    RedirectAttributes redirectAttributes) {
        log.info("remove post... " + bno);
        
        boardService.removeWithReplies(bno);
        
        redirectAttributes.addFlashAttribute("msg", bno + " 번 게시글이 삭제되었습니다.");
        
        return "redirect:/board/list";
    }

    
}
