package pl.funnyqrz.tests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@Configuration
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AbstractTest {
}
