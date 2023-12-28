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

    startLevel := 64

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

    for len(queue) > 0 {
        curr := queue[0]
        queue = queue[1:]
        if curr.x < 0 || curr.y < 0 || curr.y >= len(grid) || curr.x >= len(grid[0]) {
            continue
        }
        if grid[curr.y][curr.x] == '#' {
            continue
        }
        loc := location{x: curr.x, y: curr.y}
        if visited[loc] {
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
}
