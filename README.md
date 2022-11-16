# Udemy Study (Apache Spark 와 Scala로 빅 데이터 다루기)
기간 : 22.11.16 ~

Learning Spark (개인 Study)

### 🔹 RDD
- RDD는 Resilient Distributed Dataset 의 줄임말로 스파크의 기본 데이터 구조이다. 
- 분산 변경 불가능한 객체 모음이며 스파크의 모든 작업은 새로운 RDD를 만들거나 존재하는 RDD를 변형하거나 결과 계산을 위해 RDD에서 연산하는 것을 표현하고 있다. 
- 스파크는 빠른 map reduce 작업을 RDD 개념을 이용해 사용한다고 한다.

- Operation
  - Transformation
    기존의 RDD 를 변경하여 새로운 RDD 를 생성하는 것입니다. 즉, 리턴값이 RDD 입니다. map, filter 등을 예로 들 수 있습니다.
  - Action 
    - RDD 값을 기반으로 무엇인가를 계산해서, 결과를 생성하는 것입니다. 즉, 리턴값이 데이터 또는 실행 결과입니다. collect, count 등을 예로 들 수 있습니다

### 🔹 Spark map reduce vs Hadoop map reduce 차이
- 데이터를 메모리 vs 디스크 
- Hadoop은 기본적으로 디스크로부터 map/reduce할 데이터를 불러오고, 처리 결과를 디스크로 씀.
  - 데이터의 읽기/쓰기 속도는 느린 반면, 디스크 용량 만큼의 데이터를 한번에 처리 할 수 있습니다.

- Spark는 메모리로부터 map/reduce할 데이터를 불러오고, 처리 결과를 메모리로 씀.
  - 데이터의 읽기/쓰기 속도는 빠른 반면, 메모리 용량만큼의 데이터만 한번에 처리 할 수 있습니다.
  - 메모리 용량보다 큰 데이터를 처리 할 때는 처리 이외의 메모리 내 데이터 교체라던가, 작업 과정 저장, 컨텍스트 스위칭 등과 같은 류의 과부하가 걸릴 수도 있음.