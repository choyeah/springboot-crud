package org.zerock.board.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "tbl_member")
public class Member extends BaseEntity {

    @Id
    private String email;

    private String pwd;

    private String name;

}
