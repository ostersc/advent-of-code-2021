fun main(){

    fun part1(input: List<String>): Int {
        //count the 0s in each column
        val zeroSum=input.first().indices.map { charNum -> input.count{it[charNum]=='0'}}

        //turn them into 0 or 1 based on being the majority
        val digits=zeroSum.map { if(it <= input.size/2) '1' else '0' }

        //turn those into a string, but just flip the "bits" for the other value
        val epsilonDigits=digits.joinToString("")
        val gammaDigits=digits.map{if(it=='1') "0" else "1"}.joinToString("")

        //base 2 number from string
        return gammaDigits.toInt(2) * epsilonDigits.toInt(2)
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    //check(part2(testInput) == ???)

    val input = readInput("Day03")
    println(part1(input))
    //println(part2(input))
}