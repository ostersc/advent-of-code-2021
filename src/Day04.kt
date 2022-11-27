import kotlin.Array

data class BingoSpace(var num: Int, var marked: Boolean)

class BingoBoard {
    var grid = Array(5) { row ->
        Array(5) { col ->
            BingoSpace(0, false)
        }
    }

    fun checkWin(): Boolean {
        return grid.any { row -> row.all { it.marked } } || (0..4).any { grid[it].all { it.marked } }
    }

    fun markNumber(number: Int): Boolean {
        //iterate each col searching for the number and mark bool to true if found
        for (row in grid.iterator()) {
            for (col in row.iterator()) {
                if (col.num == number) {
                    col.marked = true
                    return true
                }
            }
        }
        return false
    }

    fun getUncalledNumberSum(): Int {
        var sum = 0
        for (row in grid.iterator()) {
            for (col in row.iterator()) {
                if (!col.marked) sum += col.num
            }
        }
        return sum
    }
}

class BingoGame(val calls: List<Int>, var boards: List<BingoBoard>) {
    var callIndex = -1

    fun callNextNumber(): Int {
        if (callIndex >= calls.size) {
            return -1
        }
        callIndex++
        for (b in boards) {
            b.markNumber(calls.get(callIndex))
        }
        return calls.get(callIndex)
    }
}

fun main() {

    fun readGame(input: List<String>): BingoGame {
        val boards = mutableListOf<BingoBoard>()
        var b = BingoBoard()
        var lineNum = 0
        for (line in input.subList(1, input.size)) {
            if (line.length == 0) {
                b = BingoBoard()
                continue
            }
            val cells = line.split(" ").filter { it.isNotEmpty() }
            for (n in cells.indices) {
                b.grid[lineNum][n] = BingoSpace(cells[n].toInt(), false)
            }
            if (lineNum++ == 4) {
                boards.add(b)
                lineNum = 0
            }
        }

        return BingoGame(input.first().split(",").map { it.toInt() }, boards)
    }

    fun part1(input: List<String>): Int {
        val g = readGame(input)
        println(g.boards.first().getUncalledNumberSum())
        var num=g.callNextNumber()
        while (num>=0) {
            for(b in g.boards){
                if(b.checkWin()){
                    return b.getUncalledNumberSum()*num
                }
            }
            num=g.callNextNumber()
        }
        return input.size
    }


    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    //check(part2(testInput) == ???)

    val input = readInput("Day04")
    println(part1(input))
    //println(part2(input))
}