DROP TABLE IF EXISTS members;

CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    age INTEGER NOT NULL,
    mbti VARCHAR(4) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_members_name_not_blank CHECK (CHAR_LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_members_age_range CHECK (age BETWEEN 0 AND 150),
    CONSTRAINT chk_members_mbti_valid CHECK (
        mbti IN (
            'INTJ', 'INTP', 'ENTJ', 'ENTP',
            'INFJ', 'INFP', 'ENFJ', 'ENFP',
            'ISTJ', 'ISFJ', 'ESTJ', 'ESFJ',
            'ISTP', 'ISFP', 'ESTP', 'ESFP'
        )
    )
) ENGINE=InnoDB;
