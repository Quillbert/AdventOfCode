package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type point struct {
	x int64
	y int64
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
        in := parts[2][2:len(parts[2])-1]
		num, _ := strconv.ParseInt(in[:len(in)-1], 16, 32)
		switch in[5] {
        case '3':
            curr.y -= num
        case '1':
            curr.y += num
        case '2':
            curr.x -= num
        case '0':
            curr.x += num
		}
        points = append(points, curr)
	}

    sum := int64(0)
    for i := 0; i < len(points) - 1; i++ {
        sum += points[i].x * points[i+1].y
        sum -= points[i].y * points[i+1].x
    }

    perimeter := int64(0)
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
