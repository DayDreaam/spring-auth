package com.ll.auth.domain.post.post.entity;import com.ll.auth.domain.member.member.entity.Member;import com.ll.auth.global.jpa.entity.BaseTime;import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTime {
    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    public void updateTitle(String title) {
        this.title=title;
    }
    public void updateContent(String content) {
        this.content=content;
    }
}
