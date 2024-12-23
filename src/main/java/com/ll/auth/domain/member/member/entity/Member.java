package com.ll.auth.domain.member.member.entity;

import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Column(unique=true,length=30)
    private String username;

    @Column(length=50)
    private String password;

    @Column(length=30)
    private String nickname;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @Builder
    public Member(String username, String password, String nickname){
        this.username=username;
        this.password=password;
        this.nickname=nickname;
    }
}
