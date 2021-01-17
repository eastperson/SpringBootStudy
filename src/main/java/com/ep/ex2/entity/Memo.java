package com.ep.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
// tbl 이름을 직접 정해준다. default 값은 camel 표기법을 underscore로 바꾼 테이블명이 만들어진다.
@Table(name="tbl_memo")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  Memo {

    @Id
    // PK 생성전략이다. Auto Increment가 된다. Entity는 모두PK가 필요하다.
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mno;

    // 기본적으로 Entity의 모든 필드는 컬럼으로 생성되지만, default 값, 타입등을 직접 지정할 때 @Column을 사용한다
    // 인덱스 등을 설정할 때도 사용이 가능하다.
    // 키는 IDENTITY이외에도 SEQUNCE, KEY TABLE등을 사용한다.
    @Column(length = 200, nullable = false)
    private String memoText;
}
