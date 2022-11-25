fun main(){
    //1791
    fun part1(input: List<String>): Int {
        var last=0
        var countOfIncreases=0
        var initialized=false
        for (i in input) {
            if(!initialized){
                initialized=true
            }else {
                if (i.toInt() > last) countOfIncreases++
            }
            last=i.toInt()
        }
        return countOfIncreases
    }

    fun part1new(input: List<String>): Int {
        val ints= input.map{it.toInt()}
        return ints.zipWithNext().count{(x, y)->y>x}
    }

    //1822
    fun part2(input: List<String>): Int {
        var prevPrev=0
        var prev=0
        var lastWindow=0

        var countOfIncreases=0
        var iterations=0

        for (i in input) {
            val curr=i.toInt()
            if(iterations++>2){
                if ( curr+prev+prevPrev > lastWindow) countOfIncreases++
            }
            lastWindow=curr+prev+prevPrev
            prevPrev=prev
            prev=curr
        }
        return countOfIncreases
    }

    fun part2new(input: List<String>): Int {
        val ints= input.map{it.toInt()}
        return ints.windowed(3).map{it.sum()}.zipWithNext().count{(x, y)->y>x}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    check(part1(testInput)==part1new(testInput))
    println(part2(input))
    check(part2(testInput)==part2new(testInput))
}