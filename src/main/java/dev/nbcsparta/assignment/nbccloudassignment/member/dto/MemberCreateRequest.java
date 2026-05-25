package dev.nbcsparta.assignment.nbccloudassignment.member.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
        @NotBlank
        @Size(max = 50)
        String name,

        @NotNull
        @Min(0)
        @Max(150)
        Integer age,

        @NotBlank
        @JsonAlias("MBTI")
        @Pattern(regexp = "^(?i:[IE][NS][FT][JP])$")
        String mbti
) {
}
