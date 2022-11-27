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
        var remaining=input
        var oxygenDigits=""
        for (i in input.first().indices){
            val zeroSum=remaining.first().indices.map { charNum -> remaining.count{it[charNum]=='0'}}
            remaining = remaining.filter { it[i].equals(if (zeroSum[i] <= remaining.size / 2) '1' else '0') }
            if(remaining.size==1) {oxygenDigits=remaining.first() ; break}
        }
        val oxygenRating=oxygenDigits.toInt(2)

        remaining=input
        var co2Digitis=""
        for (i in remaining.first().indices){
            val zeroSum=remaining.first().indices.map { charNum -> remaining.count{it[charNum]=='0'}}
            remaining = remaining.filter { it[i].equals(if (zeroSum[i] <= remaining.size / 2) '0' else '1') }
            if(remaining.size==1) {co2Digitis=remaining.first() ; break}
        }
        val co2Rating=co2Digitis.toInt(2)

        return oxygenRating*co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}