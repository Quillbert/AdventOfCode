package main

import (
    "fmt"
    "os"  
    "strconv"
    "strings"
    "unicode"
)

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.TrimSpace(string(content))
    lines := strings.Split(input, "\r\n")
    cells := make([][]rune, len(lines))
    for i:=0;i<len(cells);i++ {
        cells[i] = make([]rune, len(lines[0]))
    }
    for i, line := range lines {
        for j, c := range line {
            cells[i][j] = c
        }
    }

    for i := 0; i < len(cells); i++ {
        for j := 0; j < len(cells[i]); j++ {
            if unicode.IsDigit(cells[i][j]) {
                num := getNumber(cells, j, i);
                for j < len(cells[i]) && unicode.IsDigit(cells[i][j]) {
                    cells[i][j] = rune(num)
                    j++
                }
                j--
            } else if cells[i][j] != '*' {
                cells[i][j] = rune(0)
            } else {
                cells[i][j] = rune(-1)
            }
        }
    }

    sum := rune(0)

    stars := make(map[string]map[int]bool);

    ids := make(map[int]rune)
    id := 0

    for i := 0; i < len(cells); i++ {
        for j := 0; j < len(cells[i]); j++ {
            if cells[i][j] > 0 {
                ids[id] = cells[i][j]
                addValid(cells, j, i, stars, id)
                id++
            }
        }
    }

    for _, list := range stars {
        if len(list) == 2 {
            num := rune(1)
            for val, _ := range list {
                num *= ids[val]
            }
            sum += num
        }
    }

    fmt.Println(sum)
}

func addValid(grid [][]rune, x int, y int, m map[string]map[int]bool, id int) rune {
    num := grid[y][x]
    grid[y][x] = rune(0)
    right := rune(0)
    if x+1 < len(grid[y]) && grid[y][x+1] > rune(0) {
        right = addValid(grid, x+1, y, m, id)
    }
    if right > 0 {
        return right
    }
    for i := y-1; i <= y+1; i++ {
        for j := x-1; j <= x+1; j++ {
            if i > 0 && i < len(grid) && j > 0 && j < len(grid[i]) && grid[i][j] < 0 {
                key := fmt.Sprint(i) + "," + fmt.Sprint(j)
                if m[key] == nil {
                    m[key] = make(map[int]bool)
                }
                m[key][id] = true
                return num
            }
        }
    }
    return 0
}

func getNumber(grid [][]rune, x int, y int) int {
    if (!unicode.IsDigit(grid[y][x])) {
        return 0
    }
    out := ""
    for x < len(grid[y]) && unicode.IsDigit(grid[y][x]) {
        out += string(grid[y][x])
        x++
    }
    ret, _ := strconv.Atoi(out)
    return ret
}
