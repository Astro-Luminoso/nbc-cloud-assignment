package dev.nbcsparta.assignment.nbccloudassignment.member.dto;

import dev.nbcsparta.assignment.nbccloudassignment.member.domain.Member;

public record MemberResponse(
        Long id,
        String name,
        Integer age,
        String mbti
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }
}
