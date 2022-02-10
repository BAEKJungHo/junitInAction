# Junit5

- __JUnit Platform__
  - 테스트를 발견하고 테스트 계획을 생성하는 TestEngine 인터페이스를 정의
- __JUnit Jupiter__
  - jupiter-engine과 jupiter-api로 구성됨,
  - jupiter-api는 JUnit 5에 새롭게 추가된 테스트 코드용 API
- __JUnit Vintage__
  - 기존 JUnit 4(이하) 버전을 위한 TestEngine 구현체를 제공

## @ExtendWith

- 단위 테스트간에 공통적으로 사용할 기능 지원하기 위한 애너테이션
- org.junit.jupiter.api.extension.Extension 인터페이스의 구현체를 설정

```java
@DisplayName("단위 테스트 - mockito의 MockitoExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(MockitoExtension.class)
public class MockitoExtensionTest {
   @Mock
   private LineRepository lineRepository;
   @Mock
   private StationService stationService;

   @Test
   void findAllLines() {
       // given
       when(lineRepository.findAll()).thenReturn(Lists.newArrayList(new Line()));
       LineService lineService = new LineService(lineRepository, stationService);

       // when
       List<LineResponse> responses = lineService.findLineResponses();

       // then
       assertThat(responses).hasSize(1);
   }
}
```

## 테스트 환경 with Spring

### SpringExtension.class

- JUnit에서 Spring Boot Test 기능을 사용할 수 있게 도와줌
- @MockBean을 통해 빈을 활용할 수는 있지만 주입은 받을 수 없음
- 빈 주입을 받으려면 @Import를 통해 빈 등록을 해주어야 함

```java
@DisplayName("단위 테스트 - SpringExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(SpringExtension.class)
public class SpringExtensionTest {
   @MockBean
   private LineRepository lineRepository;
   @MockBean
   private StationService stationService;

   @Test
   void findAllLines() {
       // given
       when(lineRepository.findAll()).thenReturn(Lists.newArrayList(new Line()));
       LineService lineService = new LineService(lineRepository, stationService);

       // when
       List<LineResponse> responses = lineService.findLineResponses();

       // then
       assertThat(responses).hasSize(1);
   }
}
```

### @SpringBootTest

- 테스트에서 사용할 Context를 구축
- 스프링 기반 서버 환경을 쉽게 설정할 수 있게 도와줌
  - @SpringBootApplication을 통해 실행하는 서버의 환경과 동일한 설정
  - 스프링빈 전체를 사용하여 테스트 할 수 있음
  - 모든 빈을 사용하기 때문에 어떤 협력 객체를 등록해야 하는지에 대한 고려를 할 필요가 없음
  - 시간이 상대적으로 오래걸릴 수 있음

### @WebMvcTest

- Web과 관련된 부분(Controller 까지)의 기능을 검증 시 @WebMvcTest를 활용할 수 있음
  - 스프링 컨테이너에서 Presentation Layer에 속하는 빈(Controller, Interceptor 등)만 사용
  - 이와 관련된 설정을 손쉽게 도움(@AutoConfigureMockMvc 등)
  - Presentation Layer 외 빈을 참조하는 경우 Mock 객체 설정을 해주어야 하여 번거로울 수 있지만 상대적으로 시간이 짧게 걸림

### @DataJpaTest

- Spring Data JPA 사용 시 Repository 관련 빈을 Context에 등록하여 테스트 시 활용할 수 있게 도와줌
- 다른 Spring Data를 사용할 수 있음(ex. @DataJdbcTest, @DataRedisTest 등)

### @Profile

- 적용 될 환경(Phase)를 설정할 수 있음

```java
@Profile("dev")
public class DevConfig {
}

@Profile("!dev")
public class NotDevConfig {
}
```

### @ActiveProfiles

- Phase를 지정할 수 있음

```java
@SpringBootTest
@ActiveProfiles("dev")
public class SpringProfileTest {
    ...
}
```
