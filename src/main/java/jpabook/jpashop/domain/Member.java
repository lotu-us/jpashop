package jpabook.jpashop.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입
    private Address address;

    @OneToMany(mappedBy = "member") //member(1) : order(다) 관계
    private List<Order> orders = new ArrayList<>();
    //mappedBy : member객체가 member에서도 바뀔수있고 order에서도 바뀔수있다.
    //나는 order에 mappding된 거울일 뿐이야 라고 명시해주는 것

}
