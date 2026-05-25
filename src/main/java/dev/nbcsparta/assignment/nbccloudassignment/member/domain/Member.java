package dev.nbcsparta.assignment.nbccloudassignment.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 150;
    private static final String MBTI_PATTERN = "^[IE][NS][FT][JP]$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 4)
    private String mbti;

    private String profileKey;

    public Member(String name, Integer age, String mbti) {
        validateName(name);
        validateAge(age);

        this.name = name;
        this.age = age;
        this.mbti = normalizeMbti(mbti);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name must not be blank");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Member name must be 50 characters or fewer");
        }
    }

    private static void validateAge(Integer age) {
        if (age == null || age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Member age must be between 0 and 150");
        }
    }

    private static String normalizeMbti(String mbti) {
        if (mbti == null) {
            throw new IllegalArgumentException("Member MBTI must be valid");
        }

        String normalizedMbti = mbti.toUpperCase(Locale.ROOT);
        if (!normalizedMbti.matches(MBTI_PATTERN)) {
            throw new IllegalArgumentException("Member MBTI must be valid");
        }

        return normalizedMbti;
    }

    public void setProfileKey(String key) {
        this.profileKey = key;
    }
}
