# SonarLint 안내문
> 미션 풀이 중간에 SonarLint에서 경고했던 내역 정리 내용입니다.


## 1.

### 문제 코드
``` java
private volatile static BufferedWriter writer;
```

### 안내문
Modifiers should be declared in the correct order.
올바른 순서로 modifiers를 선언할 것.

1. Annotations 
2. public 
3. protected 
4. private 
5. abstract 
6. static 
7. sealed/non-sealed (상속 범위 명확히 제한 혹은 확장 - 사용할 일 거의 없음)
8. final 
9. transient(직렬화 대상에서 제외할 변수 지정 - 사용할 일 거의 없음. 대체 방법이 많다고 함.)
10. volatile 
11. synchronized 
12. native 
13. strictfp(부동 소수점 연산 표준화 키워드 - 사용할 일 거의 없음)

성능에 영향은 없지만 가독성을 위해 수정할 것을 권한다.

# 현재 프로그램 문제점
Logger에 있는 LOG_QUEUE는 크기가 10으로 고정되어 있지만, 이 크기를 초과해서 저장 시 제재하는 코드가 없다.
아예 저장 실패로 던지고 데이터가 유실되는 것 보단, 기다렸다가 LogCleaner가 로그를 제거해준 후에 저장하는 것이 효율적으로 보인다.
이 역시 생산자 소비사 문제의 일종이라 생각. 마지막 미션에서 처리할 필요성이 느껴진다.

# 의문점
```java
private static BufferedWriter writer;
```
에서 volatile 키워드의 필요성 여부가 궁금하다. 멀티 스레드 환경에서는 BufferedWriter의 초기화 등으로 인한 메모리 가시성 문제가 발생할 것 같은데... 이 writer를 사용해 로그 파일을 작성하는 메서드에서 `synchronized` 키워드로 락 제어를 하기 때문에 문제가 없는 것인지, 그럼에도 필요한 것인지 의문이 든다.