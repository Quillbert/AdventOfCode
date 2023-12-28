package main

import (
    "fmt"
    "os"
    "strings"
)

type queueEntry struct {
    x int
    y int
    level int
}

type location struct {
    x, y int
}

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")    
    lines := strings.Split(input, "\n")
    grid := make([][]byte, len(lines))
    
    for i, line := range lines {
        grid[i] = []byte(line)
    }

    startLevel := 26501365

    evenCount := 0
    oddCount := 0

    queue := make([]queueEntry, 0)

    for i, line := range grid {
        for j, c := range line {
            if c == 'S' {
                queue = append(queue, queueEntry{x: j, y: i, level: startLevel})
            }
        }
    }

    visited := make(map[location]bool)
    oldVisited := make(map[location]bool)
    oldOldVisited := make(map[location]bool)

    max := startLevel + 1

    for len(queue) > 0 {
        curr := queue[0]
        queue = queue[1:]
        if curr.level < max {
            oldOldVisited = oldVisited
            oldVisited = visited
            visited = make(map[location]bool)
            max = curr.level
            fmt.Printf("Odd: %v, Even: %v, Level: %v\n", oddCount, evenCount, startLevel - max - 1)
        }
        if grid[mod(curr.y, len(grid))][mod(curr.x, len(grid[0]))] == '#' {
            continue
        }
        loc := location{x: curr.x, y: curr.y}
        if visited[loc] || oldVisited[loc] || oldOldVisited[loc] {
            continue
        }
        visited[loc] = true
        if curr.level % 2 == 0 {
            evenCount++
        } else {
            oddCount++
        }
        if curr.level > 0 {
            queue = append(queue, queueEntry{x: curr.x + 1, y: curr.y, level: curr.level - 1})
            queue = append(queue, queueEntry{x: curr.x - 1, y: curr.y, level: curr.level - 1})
            queue = append(queue, queueEntry{x: curr.x, y: curr.y - 1, level: curr.level - 1})
            queue = append(queue, queueEntry{x: curr.x, y: curr.y + 1, level: curr.level - 1})
        }
    }
    fmt.Println(evenCount)
    fmt.Println(oddCount)
}

func mod(x, y int) int {
    out := x % y
    if out < 0 {
        out += y
    }
    return out
}
