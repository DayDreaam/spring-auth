package com.ll.auth.domain.member.member.service;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder;

    public long getCount(){
        return memberRepository.count();
    }

    public Member join(String username, String password, String nickname){
        Member member = Member.builder()
                .username(username)
                //.password(passwordEncoder.encode(password))
                .password(password)
                .nickname(nickname)
                .build();
        memberRepository.save(member);
        return member;
    }

    public boolean alreadyExists(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username).get();
    }
}
