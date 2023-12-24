package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type point struct {
	x int
	y int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")

	curr := point{x: 0, y: 0}
    points := make([]point, 0, len(lines) + 1)
    points = append(points, curr)

	for _, line := range lines {
		parts := strings.Fields(line)
		num, _ := strconv.Atoi(parts[1])
		switch parts[0] {
        case "U":
            curr.y -= num
        case "D":
            curr.y += num
        case "L":
            curr.x -= num
        case "R":
            curr.x += num
		}
        points = append(points, curr)
	}

    sum := 0
    for i := 0; i < len(points) - 1; i++ {
        sum += points[i].x * points[i+1].y
        sum -= points[i].y * points[i+1].x
    }

    perimeter := 0
    for i := 0; i < len(points) - 1; i++ {
        num := points[i].x - points[i+1].x + points[i].y - points[i+1].y
        if num < 0 {
            num = -num
        }
        perimeter += num
    }

    sum /= 2
    sum += perimeter/2
    sum++
    fmt.Println(sum)
}
