package com.ll.auth.domain.member.member.dto;

import com.ll.auth.domain.member.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberDto {
    private long id;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String nickname;

    public MemberDto(Member member){
        this.id=member.getId();
        this.createAt=member.getCreatedDate();
        this.modifiedAt=member.getModifiedDate();
        this.nickname=member.getNickname();
    }

}
