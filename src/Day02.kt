fun main(){

    fun part1(input: List<String>): Int {
       val dirMap= input.groupBy ( keySelector = {it.split(" ").get(0)}, valueTransform = { it.split(" ").get(1).toInt()})

       return dirMap.get("forward")!!.sum() * (dirMap.get("down")!!.sum() -dirMap.get("up")!!.sum())
    }


    fun part2(input: List<String>): Int {
        var aim=0
        var horizontal=0
        var depth=0

        for(i in input){
            val instruction=i.split(" ").get(0)
            val inc=i.split(" ").get(1).toInt()

            when(instruction){
                "forward" ->{horizontal+=inc; depth+=aim*inc}
                "up" -> aim-=inc
                "down" -> aim+=inc
            }
        }
        return horizontal*depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}