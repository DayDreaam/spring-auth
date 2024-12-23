package com.ll.auth.domain.post.post.controller;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.domain.post.post.dto.PostDto;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import com.ll.auth.global.rsdata.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public List<PostDto> getItems() {
        return postService.findAllByOrderByIdDesc()
                .stream()
                .map(PostDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getItem(@PathVariable long id){
        Post post = postService.getPost(id);
        return new PostDto(post);
    }

    @DeleteMapping("/{id}")
    public RsData<Void> deleteItem(@PathVariable long id){
        Post post = postService.getPost(id);
        postService.delete(post);
        return new RsData<>("200-1",
                "%s번 글이 삭제되었습니다.".formatted(id));
    }

    record PostModifyReqBody(
            @NotBlank
            @Length(min=2)
            String title,
            @NotBlank
            @Length(min=2)
            String content
    ){}

    @PutMapping("/{id}")
    @Transactional
    public RsData<PostDto> modifyItem(
            @PathVariable Long id,
            @RequestBody @Valid PostModifyReqBody reqBody
    ){
        Post post = postService.getPost(id);
        postService.modify(post,reqBody.title,reqBody.content);
        return new RsData<>("200-1",
                "%s번 글이 수정되었습니다.".formatted(id),
                new PostDto(post));
    }

    record PostWriteReqBody(
            @NotBlank
            @Length(min=2)
            String title,
            @NotBlank
            @Length(min=2)
            String content
    ){}

    record PostWriteResBody(
            PostDto item,
            long totalCount
    ){}

    @PostMapping
    @Transactional
    public RsData<PostWriteResBody> writeItem(
            @RequestBody @Valid PostWriteReqBody reqBody
    ){
        Member author = memberService.findByUsername("user3");
        Post post = postService.write(author, reqBody.title,reqBody.content);
        return new RsData<PostWriteResBody>("200-1",
                "글이 등록되었습니다.",
                new PostWriteResBody(
                        new PostDto(post),
                        postService.getCount())
        );
    }
}
