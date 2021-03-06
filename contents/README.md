# Issues

### [#issue1] Junit 이란 ?

- Junit 은 자바용 단위 테스트 작성을 위한 표준 프레임워크를 의미한다.

> 프레임워크 : 여러 애플리케이션에서 공유할 수 있는 재활용 가능하고 보편적인 구조

### [#issue2] 단위 테스트란 ?

- 원래 단위 테스트라는 용어는 작업의 한 단위(unit)의 동작을 검사하는 테스트를 의미한다.
- 단위 테스트라는 용어는 시간이 흐르면서 더 넓은 의미들을 갖게 되었다.
  - Ex. IEEE 의 정의에서는 '독립적인 하드웨어나 소프트웨어 단위 혹은 관련 단위들의 집합에 대한 테스트' 라고 되어 있다.
- Junit In Action 책에서는 단위 테스트라는 용어를 `다른 단위들에 종속되지 않은 하나의 단위에 대한 테스트`라는 의미로 사용하고 있다.

> 자바 애플리케이션에서 명확한 작업의 단위는 일반적으로 하나의 메서드로 간주된다. 반면, 통합 테스트(Integration Test)나 인수 테스트는 다양한 컴포넌트 사이의 상호작용을 테스트한다.

### [#issue3] API 계약

- 단위 테스트로 메서드가 `API 계약`을 제대로 따르는지 확인하기도 한다.
- API 계약은 호출자와 피호출자간의 공식 합의로 바라보는 관점을 의미한다.
  - API 계약은 메서드의 승인으로 만들어진 공식적인 합의이다.
  - Ex. 카드 자동결제 API 를 사용하기 위해서 Key 를 제공하면, 해당 API 를 사용할 수 있다. 만약에 카드 자동결제 API 를 사용하기 위해서 요청으로 온 Key 값을 처리하는 메서드가 계약을 만족시키지 못한다면, 이를 `메서드가 계약을 위반했다`라고 한다.

### [#issue4] 단위 테스트의 몇 가지 중요한 개념

- __setFixtures__
  - 픽스처(fixture) 란, 테스트를 실행하기 위해 사전에 필요한 것들이라고 생각하면 된다.
  - setFixtures() 혹은 setUp() 이라는 메서드로 사용된다.
  - `Junit4`
    - @BeforeClass or @Before 과 같이 사용된다.
  - `Junit5`
    - @BeforeAll or @BeforeEach 와 같이 사용된다.
- __tearDown__
  - 해체라는 의미를 지니고 있다. 즉, 테스트 종료 후 공통적으로 처리해줘야 하는 로직들을 tearDown() 에 작성하곤 한다.
  - `Junit4`
    - @AfterClass or @After 과 같이 사용된다.
  - `Junit5`
    - @AfterAll or @AfterEach 와 같이 사용된다.

> [JPA boilerplate code](https://github.com/BAEKJungHo/junitInAction/blob/main/contents/chapter18/README.md#jpa-boilerplate-code)

## References

- https://junit.org/junit5/docs/current/user-guide/
- https://docs.python.org/ko/3/library/unittest.html
