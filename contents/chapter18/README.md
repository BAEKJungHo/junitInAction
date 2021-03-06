# JPA 기반 애플리케이션 테스트 하기

## DAO or Repository

자바 애플리케이션은 데이터를 영구 저장하기 위해서 RDBMS 를 주로 사용한다. 데이터를 영구 저장하기 위한 방법이 존재하는데 다음과 같다.

- SQL 문을 직접 작성
- JDBC 프로그래밍을 쉽게 해주는 서드파티 툴 사용
  - Ex. MyBatis, Spring JDBC Templates
- 객체를 SQL 에 매핑하고, SQL 을 객체로 매핑 해주는 ORM 툴을 사용하여 데이터베이스 액세스 관련 전체를 위임

전 세계적으로 세 번째의 사용 빈도가 앞도적으로 높다. 대한민국 SI 시장에서는 JPA 보다 MyBatis 비중이 더 높은것으로 알고 있다.

번외로 계층을 나누면 무슨 이점이 있을까 ?

- 각 계층간에 별도로 테스트가 가능하다.
- 계층이 나눠져 있어서 코드의 응집도가 높아지고, 결합도가 낮아져서 코드의 유지보수성과 확장성, 재사용성이 용이하다.
  - 예를들어 Controller 에서 View 와 관련된 행동, 비지니스 로직, 데이터 접근등을 다 처리한다면.. 상상만해도 끔찍하다... 그만 상상하자.

## 계층형 애플리케이션

계층형 애플리케이션이란 애플리케이션의 구조가 여러 계층으로 구분되어있는 것을 뜻한다.

웹 애플리케이션은 `표현(Presentation Layer)`, `비지니스(Business Layer)`, `영속(Persistence Layer)` 의 세 계층으로 나뉜다.

각 계층간 단위테스트와 통합 테스트가 이루어져야 한다. 

__기능을 인터페이스와 구현으로 나누는 방식은 애플리케이션 개발을 촉진시킨다. 여러 팀에 의해 병렬 개발할 수 있고, 개발자는 영속 계층 없이도 전체 비지니스 계층을 개발하고 단위 테스트를 할 수 있다.__

## JPA 테스트의 특징

JPA 인터페이스를 위한 목(Ex. eazymock)을 사용하면 영속 계층도 테스트할 수 있지만, 추천하는 방식은 아니다.

추천하지 않은 이유는 다음과 같다.

- 목은 API 호출만 모방(emulation)할 뿐이다.
- JPA 를 사용하기 위해 어노테이션 등과 같은 설정 파일을 제공해야 한다.
- JPA 부분이 설정되어있다 하더라도 JPA 벤더(Ex. Hibernate)와 사용할 DB 드라이버(Ex. HibernateDialect) 등의 결함, 데이터베이스에서 받아들일 수 없는 테이블명, 트랜잭션 문제 등 목(Mock) 테스트만으로 검출할 수 없는 문제들이 런타임에 발생할 수 있다.

__따라서, 영속 계층의 경우 데이터베이스에 실제로 접근하여 테스트 해보는 것이 중요하다.__

### 무엇을 테스트 해야 하는가?

JPA 프로그래밍은 `엔티티 매핑`과 `API 호출` 로 두 부분으로 구분 된다. 

1. 엔티티가 어떻게 데이터베이스 테이블에 매핑되는지 정의
2. EntityManager 를 사용하여 데이터베이스를 조회하거나 저장 등 수행

따라서, 위 두 부분은 `독립적(independent)`으로 테스트하는 것이 좋다.

## 임베디드 데이터베이스의 장점

단위 테스트는 빨라야 한다. 하지만 영속 계층을 테스트하는 경우 DB에 액세스하는 시간이 느리다. (일반적으로 DB 액세스는 느리다.)

SQL 생성은 JPA 벤더가 알아서 해주고, JPA 벤더 대부분은 잘 알려진 DB 들은 다 지원한다.

따라서, `H2 와 같은 임베디드 데이터베이스`를 사용하여 영속 계층에 대한 단위 테스트 속도를 올릴 수 있다.

> 테스트용 DB 설정과, 개발용 DB 설정을 따로 가져가면 좋다.

## JPA boilerplate code

```java
@Slf4j
class GenericJPATest {
 
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;
 
    @BeforeAll
    static void setFixturesAll() throws Exception {
        emf = Persistence.createEntityManagerFactory("[test-persistence-unit]");
    }
 
    @AfterAll
    static void tearDownAll() throws Exception {
        if (emf != null) {
            emf.close();
        }
    }
 
    @BeforeEach
    void setFixtures() throws Exception {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }
 
    @AfterEach
    void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
    }
 
    @Test
    void test() throws Exception {
        transaction 과 em 을 사용하여 테스트
    }
}
```
