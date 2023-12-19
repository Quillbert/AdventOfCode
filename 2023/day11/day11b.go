package main

import (
    "fmt"
    "os"
    "strings"
)

type location struct {
    x int
    y int
}

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
    lines := strings.Split(input, "\n")

    grid := make([][]byte, len(lines))
    for i, line := range lines {
        grid[i] = []byte(line)
    }

    yGaps := make([]int, 0)

    for i := 0; i < len(grid); i++ {
        empty := true
        for j := 0; j < len(grid[i]); j++ {
            if grid[i][j] == '#' {
                empty = false
                break
            }
        }
        if empty {
            yGaps = append(yGaps, i)
        }
    }

    xGaps := make([]int, 0)

    for i := 0; i < len(grid[0]); i++ {
        empty := true
        for j := 0; j < len(grid); j++ {
            if grid[j][i] == '#' {
                empty = false
                break
            }
        }
        if empty {
            xGaps = append(xGaps, i)
        }
    }

    places := make([]location, 0)

    for i, line := range grid {
        for j, c := range line {
            if c == '#' {
                x := j
                for _, v := range xGaps {
                    if v > j {
                        break
                    }
                    x += 999_999
                }
                y := i
                for _, v := range yGaps {
                    if v > i {
                        break
                    }
                    y += 999_999
                }
                newPlace := location{x: x, y: y}
                places = append(places, newPlace)
            }
        }
    }

    sum := 0

    for i := 0; i < len(places); i++ {
        for j := i + 1; j < len(places); j++ {
            sum += abs(places[i].x - places[j].x) + abs(places[i].y - places[j].y)
        }
    }
    
    fmt.Println(sum)
}

func abs(x int) int {
    if x < 0 {
        return -x
    }
    return x
}
