# Udemy Study (Apache Spark 와 Scala로 빅 데이터 다루기)
기간 : 22.11.16 ~

Learning Spark (개인 Study)

### 🔹 RDD(Resilient Distributed Dataset)
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


### 🔹 SECTION4. SPARK SQL, DataFrames 및 DataSets
- 데이터 프레임
  - 관계성 데이터베이스
  - 스키마를 가지고 효율적인 저장
  - 스키마와 row가 있으므로 SQL 데이터베이스처럼 다룰 수 있음
  - JSON, HIVE, Parquet를 읽고 쓸 수 있다
  - JDBC/ODBC, Tableau을 통해 연결

- 데이터 셋
  - 장점
    - 기술 면에서, 데이터 프레임은 Row 객체의 데이터 셋일 뿐
    - 명확한 타입을 둘러 쌈
    - 컴파일 타입에 앞선 스키마를 알 수 있는 명쾌한 타입
    - 데이터 프레임과 다르게 알 수 있는 것은 Row의 데이터 셋이 있다는 것과 Row는 정의할 때까지 무엇이든 담을 수 있음
    - 컴파일 타임의 스키마를 알고있음
    - 빅 클러스터에서의 스크립트 가동처럼 비용이 많이 드는 유형들과 관련된 오류를 컴파일 타임에 발견할 수 있음.
    - RDD를 .toDS 데이터셋으로 변환할 수 있고 다른 방식으로도 가능

  - 단점
    - 이 모든 것이 컴파일 타임에 이루어지기 때문에 컴파일된 언어로만 사용가능
    - 자바나 스칼라 같은 것들
    - 파이썬 대신 스칼라를 사용하는 이유
  
- Spark 개발 전반적인 트렌드
  - RDD를 적게 쓰고 데이터 셋을 많이 씀
  - 컴파일 타임에 결정하는 수행 플랜 때문에 SQL 스타일의 쿼리를 쓰면 RDD보다 효과적으로 사용
  - Spark 머신러닝 라이브러리와 Spark 스트리밍 엔진은 데이터 셋을 RDD 대신 API를 주로 이용
  - 데이터셋의 사용은 개발을 아주 단순화할 수 있음

- Scala에서 Spark SQL 사용방법
  - 스크립트를 시작할때 SparkContext 대신 SparkSession을 사용하게 될 것이라는 점
  - 작업을 완료하면 세션을 명확히 정지해야함

- Spark Shell
  - Spark SQL에서 셸을 열 수 있게 하고 데이터베이스처럼 설계가능

- 사용자 정의함수(USER-DEFINED FUNCTIONS(UDF'S))
  - import org.apache.spark.sql.functions.udf
  - 내가 만든 함수를 호출해서 사용

### 🔹 SECTION5. 스파크 프로그램의 고급 사례
  
- Hive
  - 