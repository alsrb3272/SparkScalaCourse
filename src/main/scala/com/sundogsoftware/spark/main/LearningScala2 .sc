// Flow control

// If / else :
if (1>3) println("Impossible") else println("The world makes sense.")

if (1 > 3){
  println("Impossible")
  println("Really?")

} else{
  println("The world makes sense.")
  println("still.")
}

// matching
val number = 100
number match {
  case 1 => println("One")
  case 10 => println("ten")
  case 2 => println("two")
  case _ => println("Something else")
}

for (x <- 1 to 4){
  val squared = x * x
  println(squared)
}

var x = 10
while (x>=0){
  println(x)
  x -= 1
}

x = 0
do { println(x); x+=1} while (x<=10)

// Expressions

{val x = 10; x + 20}

println({val x = 10; x + 20})

// EXERCISE
// Write some code that prints out the first 10 values of the Fibonacci sequence.
// This is the sequence where every number is the sum of the two numbers before it.
// So, the result should be 0, 1, 1, 2, 3, 5, 8, 13, 21, 34

// 이것은 모든 숫자가 그 앞에 있는 두 숫자의 합인 시퀀스입니다.
// 따라서 결과는 0, 1, 1, 2, 3, 5, 8, 13, 21, 34여야 합니다.


def fib(n:Int, a:Int = 0, b:Int = 1): List[Int] = {
  if(a > n) Nil
  else a +: fib(n, b, a+b)
}
print(fib(34).mkString(", "))

