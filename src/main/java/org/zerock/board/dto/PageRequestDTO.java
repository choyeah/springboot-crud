package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    
    // 검색 관련 필드 추가
    private String type;    // 검색 타입
    private String keyword; // 검색 키워드
    
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
    
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
    
    // 검색 조건을 배열로 변환
    public String[] getTypes() {
        if(type == null || type.trim().length() == 0) {
            return null;
        }
        return type.split("");
    }
} 