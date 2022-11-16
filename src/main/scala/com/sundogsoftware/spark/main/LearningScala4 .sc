// Data structures

// Tuples
// Immutable lists

val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")
println(captainStuff)

// Refer to the individual fields with a ONE-BASED index
println(captainStuff._1)
println(captainStuff._2)
println(captainStuff._3)

val picardsShip = "Picard" -> "Enterprise-D"
println(picardsShip._2)

val aBunchOfStuff = ("Kirk", 1964, true)


// Lists
// Like a tuple, but more functionality
// Must be of same type

val shipList = List("Enterprise", "Defiant", "Voyager", "Deep Space Nine")

println(shipList(0))
// zero-based

println(shipList.head)
println(shipList.tail)

for (ship <- shipList) {println(ship)}

val backwardShips = shipList.map( (ship: String) => {ship.reverse})
for(ship <- backwardShips) {println(ship)}

// reduce() to combine together all the items in a collection using some function
val numberList = List(1, 2, 3, 4, 5)
val sum = numberList.reduce((x:Int, y:Int) => x + y)
println(sum)

// filter() remove stuff
val iHateFives = numberList.filter( (x:Int) => x != 5)

val iHateThrees = numberList.filter(_ != 3)

// Concatenate lists
val moreNumbers = List(6,7,8)
val lotsOfNumbers = numberList ++ moreNumbers

val reversed = numberList.reverse
val sorted = reversed.sorted
val lotsOfDuplicates = numberList ++ numberList
val distinctValues = lotsOfDuplicates.distinct
val maxValue = numberList.max
val minValue = numberList.min
val total = numberList.sum
val hasThree = iHateThrees.contains(3)

// MAPS
val shipMap = Map("Kirk"-> "Enterprise", "Picard" -> "Enterprise-D","Sisko"-> "Deep Space Nine", "Janeway" -> "Voyager")
println(shipMap("Janeway"))
println(shipMap.contains("Archer"))
val archersShip = util.Try(shipMap("Archer")) getOrElse "Unknown"
println(archersShip)

// EXERCISE
// 1-20까지의 숫자 목록을 만듭니다.
// 당신의 임무는 3으로 균등하게 나누어지는 숫자를 출력하는 것입니다.
// (다른 언어와 마찬가지로 스칼라의 모듈라 연산자는 나눗셈 후 나머지를 제공하는 %입니다.
// 예를 들어 9는 3으로 균등하게 나눌 수 있기 때문에 9 % 3 = 0입니다.)
// 먼저 목록의 모든 항목을 반복하여 수행하고 이동하면서 하나하나 테스트합니다.
// 그런 다음 목록에서 필터 기능을 대신 사용하여 다시 수행하십시오.
val numberList2 = List(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)

for (remain <- numberList2) {
  val remain2 = remain % 3
  if(remain2 == 0){
    println(remain)
  }else{
    null}
}
