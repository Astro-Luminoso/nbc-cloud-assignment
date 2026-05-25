package dev.nbcsparta.assignment.nbccloudassignment.member;

import dev.nbcsparta.assignment.nbccloudassignment.member.domain.Member;
import dev.nbcsparta.assignment.nbccloudassignment.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:member-api-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void createMemberPersistsAndReturnsCreatedMember() throws Exception {
        String request = """
                {
                  "name": "Kim",
                  "age": 27,
                  "mbti": "INTJ"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Kim"))
                .andExpect(jsonPath("$.age").value(27))
                .andExpect(jsonPath("$.mbti").value("INTJ"));

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getName()).isEqualTo("Kim");
        assertThat(members.getFirst().getAge()).isEqualTo(27);
        assertThat(members.getFirst().getMbti()).isEqualTo("INTJ");
    }

    @Test
    void createMemberNormalizesLowercaseMbti() throws Exception {
        String request = """
                {
                  "name": "Choi",
                  "age": 29,
                  "mbti": "enfp"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mbti").value("ENFP"));

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getMbti()).isEqualTo("ENFP");
    }

    @Test
    void createMemberAcceptsUppercaseMbtiAlias() throws Exception {
        String request = """
                {
                  "name": "Jung",
                  "age": 33,
                  "MBTI": "isfj"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mbti").value("ISFJ"));

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getMbti()).isEqualTo("ISFJ");
    }

    @Test
    void createMemberRejectsInvalidValidationPayloads() throws Exception {
        List<String> requests = List.of(
                createRequest("\" \"", "24", "\"INTJ\""),
                createRequest("\"" + "A".repeat(51) + "\"", "24", "\"INTJ\""),
                createRequest("\"Park\"", "null", "\"INTJ\""),
                createRequest("\"Park\"", "-1", "\"INTJ\""),
                createRequest("\"Park\"", "151", "\"INTJ\""),
                createRequest("\"Park\"", "24", "\" \""),
                createRequest("\"Park\"", "24", "\"ABCD\"")
        );

        for (String request : requests) {
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest());
        }

        assertThat(memberRepository.findAll()).isEmpty();
    }

    @Test
    void createMemberRejectsInvalidMbtiWithoutPersisting() throws Exception {
        String request = """
                {
                  "name": "Park",
                  "age": 24,
                  "MBTI": "ABCD"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        assertThat(memberRepository.findAll()).isEmpty();
    }

    @Test
    void createMemberRejectsMalformedJson() throws Exception {
        String request = """
                {
                  "name": "Park",
                  "age": 24,
                  "mbti": "INTJ"
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        assertThat(memberRepository.findAll()).isEmpty();
    }

    @Test
    void getMemberReturnsStoredMember() throws Exception {
        Member member = memberRepository.save(new Member("Lee", 31, "ENFP"));

        mockMvc.perform(get("/api/members/{id}", member.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.name").value("Lee"))
                .andExpect(jsonPath("$.age").value(31))
                .andExpect(jsonPath("$.mbti").value("ENFP"));
    }

    @Test
    void getMemberReturnsNotFoundWhenMissing() throws Exception {
        mockMvc.perform(get("/api/members/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", matchesPattern("Member not found\\. id=999")));
    }

    private static String createRequest(String name, String age, String mbti) {
        return """
                {
                  "name": %s,
                  "age": %s,
                  "mbti": %s
                }
                """.formatted(name, age, mbti);
    }
}
