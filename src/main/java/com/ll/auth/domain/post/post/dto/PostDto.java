package com.ll.auth.domain.post.post.dto;

import com.ll.auth.domain.post.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostDto {
    private long id;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;

    public PostDto(Post post){
        this.id=post.getId();
        this.createAt=post.getCreatedDate();
        this.modifiedAt=post.getModifiedDate();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.authorId=post.getAuthor().getId();
        this.authorName=post.getAuthor().getNickname();
    }
}
