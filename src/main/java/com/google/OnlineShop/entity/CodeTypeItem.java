package com.google.OnlineShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "code_type_item")
public class CodeTypeItem {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "code_type_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CodeType codeType;

    @NotNull
    @Column(name = "code_type_id", nullable = false)
    private Long codeTypeId;

    @NotNull
    @Column(name = "english_title", nullable = false)
    private String englishTitle;

    @NotNull
    @Column(name = "farsi_title", nullable = false)
    private String farsiTitle;
}
