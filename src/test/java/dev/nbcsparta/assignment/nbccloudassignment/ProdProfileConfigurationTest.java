package dev.nbcsparta.assignment.nbccloudassignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

class ProdProfileConfigurationTest {

    private static final String PROD_DB_URL = "jdbc:mysql://db.example.com:3306/nbc_cloud_assignment";

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withPropertyValues(
                    "spring.profiles.active=prod",
                    "DB_URL=" + PROD_DB_URL,
                    "DB_USERNAME=prod_user",
                    "DB_PASSWORD=prod_password"
            )
            .withInitializer(new ConfigDataApplicationContextInitializer());

    @Test
    void prodProfileLoadsExpectedDatabaseAndSafetySettings() {
        contextRunner.run(context -> {
            assertThat(context.getStartupFailure()).isNull();

            Environment environment = context.getEnvironment();
            assertThat(environment.getProperty("spring.h2.console.enabled", Boolean.class)).isFalse();
            assertThat(environment.getProperty("spring.sql.init.mode")).isEqualTo("never");
            assertThat(environment.getProperty("spring.jpa.hibernate.ddl-auto")).isEqualTo("validate");
            assertThat(environment.getProperty("spring.datasource.driver-class-name"))
                    .isEqualTo("com.mysql.cj.jdbc.Driver");
            assertThat(environment.getProperty("spring.datasource.url")).isEqualTo(PROD_DB_URL);
        });
    }
}
