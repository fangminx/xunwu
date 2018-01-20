package com.fangminx;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 测试类父类，其他测试类继承它
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@ActiveProfiles("test") //在单元测试走 application-text.properties
public class ApplicationTests {
}
