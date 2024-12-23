package com.ll.auth.domain.member.member.controller;

import com.ll.auth.domain.member.member.dto.MemberDto;
import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.global.exceptions.ServiceException;
import com.ll.auth.global.rsdata.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;

    record signupReqBody(
            @NotBlank
            @Length(min=4)
            String username,
            @NotBlank
            @Length(min=6)
            String password,
            @NotBlank
            @Length(min=4)
            String nickname
    ){}
    record signupResBody(
            MemberDto item
    ){}

    @PostMapping("/signup")
    @Transactional
    public RsData<signupResBody> writeItem(
            @RequestBody @Valid ApiV1MemberController.signupReqBody reqBody
    ){
        if(memberService.alreadyExists(reqBody.username)){
            throw new ServiceException("400-1","해당 username은 이미 사용중입니다.");
        }
        Member member = memberService.join(reqBody.username, reqBody.password, reqBody.nickname);
        return new RsData<>("200-1",
                "%s님 환영합니다.".formatted(member.getNickname()),
                new signupResBody(
                        new MemberDto(member))
        );
    }
}
