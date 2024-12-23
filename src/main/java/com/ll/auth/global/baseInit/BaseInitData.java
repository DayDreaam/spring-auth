package com.ll.auth.global.baseInit;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final PostService postService;
    private final MemberService memberService;
    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.getCount() > 0) {
            return;
        }

        Member memberSystem = memberService.join("system", "1234", "시스템");
        Member memberAdmin = memberService.join("admin", "1234", "관리자");
        Member member1 = memberService.join("user1", "1234", "유저1");
        Member member2 = memberService.join("user2", "1234", "유저2");
        Member member3 = memberService.join("user3", "1234", "유저3");

        Post post1 = postService.write(member1, "축구 하실 분?", "14시 까지 22명을 모아야 합니다.");
        Post post2 = postService.write(member2, "배구 하실 분?", "15시 까지 12명을 모아야 합니다.");
        Post post3 = postService.write(member3, "농구 하실 분?", "16시 까지 10명을 모아야 합니다.");

    }

}