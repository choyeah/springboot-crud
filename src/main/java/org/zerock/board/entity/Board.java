package org.zerock.board.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
@Table(name = "tbl_board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;
    
    //관계형 데이터베이스 관점에서, 
    //FK(Board)를 사용하는 엔티티가 PK(Member)를 가진 엔티티를 참조하는 구조로 설계하면 데이터베이스 구조와 일치해서 편함

    @ManyToOne (fetch = FetchType.LAZY)
    private Member writer;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}

