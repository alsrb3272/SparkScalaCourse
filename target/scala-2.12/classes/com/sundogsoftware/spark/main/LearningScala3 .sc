// Functions

// format def <function name> (parameter name : type...) : return type = { }

def squareIt(x: Int) : Int = {
  x * x
}
println(squareIt(2))

def cubeIt(x : Int) : Int = {x * x * x}

println(cubeIt(2))

def transformInt(x: Int, f: Int => Int): Int = {
  f(x)
}

val result = transformInt(2, cubeIt)
println(result)


transformInt(3, x=> x * x * x)

transformInt(10, x=> x/2)

transformInt(2, x=> {val y = x *2 ; y * y})

// EXERCISE
// Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
// Write a function that converts a string to upper-case, and use that function of a few test strings.
// Then, do the same thing using a function literal instead of a separate, named function.

/*
문자열에는 .toUpperCase 메서드가 내장되어 있습니다. 예를 들어 "foo".toUpperCase는 FOO를 반환합니다.
문자열을 대문자로 변환하는 함수를 작성하고 몇 가지 테스트 문자열의 해당 함수를 사용하십시오.
그런 다음 별도의 명명된 함수 대신 함수 리터럴을 사용하여 동일한 작업을 수행합니다.*/

def transformVal(x : String){
 println(x.toUpperCase())
}

transformVal("hello")

// 리터럴을