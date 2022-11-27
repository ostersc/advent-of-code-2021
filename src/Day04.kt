data class BingoSpace(var num: Int, var marked: Boolean)

class BingoBoard {
    var grid = Array(5) { row ->
        Array(5) { col ->
            BingoSpace(0, false)
        }
    }

    override fun toString(): String {
        var s=""
        for (col in 0..4) {
            for (row in 0..4) {
                val b =grid.get(col).get(row)
                if(b.marked) s=s.plus("[") else  s=s.plus(" ")
                s=s.plus(b.num.toString().padStart(2))
                if(b.marked) s=s.plus("]") else  s=s.plus(" ")
                if(row==4) s=s.plus("\n")
            }
        }
        if(checkWin()){
            s=s.plus("     WINNER\n")
        }else{
            s=s.plus("     not a win\n")
        }
        return s
    }

    fun checkWin(): Boolean {
        return grid.any { row -> row.all { it.marked } } ||
                (0..4).any {col -> (0..4).all{row->grid[row][col].marked }}
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
        callIndex++
        if (callIndex >= calls.size) {
            return -1
        }

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
        var num = g.callNextNumber()
        while (num >= 0) {
            for (b in g.boards) {
                if (b.checkWin()) {
                    return b.getUncalledNumberSum() * num
                }
            }
            num = g.callNextNumber()
        }
        return -1
    }


    fun part2(input: List<String>): Int {
        val g = readGame(input)
        var num = g.callNextNumber()
        var lastWinSum = 0
        val remainingBoards = ArrayList(g.boards)
        while (num >= 0) {
            //println("Just called ${num}")
            val iter = remainingBoards.iterator()
            while (iter.hasNext()) {
                val b = iter.next()
                //println(b)
                if (b.checkWin()) {
                    lastWinSum = b.getUncalledNumberSum() * num
                    iter.remove()
                }
            }
            num = g.callNextNumber()
        }
        return lastWinSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}