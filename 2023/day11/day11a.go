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

    for i := 0; i < len(grid); i++ {
        empty := true
        for j := 0; j < len(grid[i]); j++ {
            if grid[i][j] == '#' {
                empty = false
                break
            }
        }
        if empty {
            newLine := make([]byte, len(grid[0]))
            copy(newLine, grid[i])
            grid = append(grid[:i+1], grid[i:]...)
            grid[i] = newLine
            i++
        }
    }

    for i := 0; i < len(grid[0]); i++ {
        empty := true
        for j := 0; j < len(grid); j++ {
            if grid[j][i] == '#' {
                empty = false
                break
            }
        }
        if empty {
           for j := 0; j < len(grid); j++ {
               grid[j] = append(grid[j][:i+1], grid[j][i:]...)
           }
           i++
        }
    }

    places := make([]location, 0)

    for i, line := range grid {
        for j, c := range line {
            if c == '#' {
                newPlace := location{x: j, y: i}
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
