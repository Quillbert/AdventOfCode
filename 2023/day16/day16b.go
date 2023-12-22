package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")
	grid := make([][]byte, len(lines))
	for i, line := range lines {
		grid[i] = []byte(line)
	}
	max := 0
	for i := 0; i < len(grid[0]); i++ {
        t := wrapper(grid, i, 0, 0, 1)
        b := wrapper(grid, i, len(grid) - 1, 0, -1)
        if t > max {
            max = t
        }
        if b > max {
            max = b
        }
	}
    for i := 0; i < len(grid); i++ {
        l := wrapper(grid, 0, i, 1, 0)
        r := wrapper(grid, len(grid[0])-1, i, -1, 0)
        if l > max {
            max = l
        }
        if r > max {
            max = r
        }
    }
	fmt.Println(max)
}

func wrapper(grid [][]byte, x, y, xdir, ydir int) int {
	track := make([][]bool, len(grid))
	for i := 0; i < len(grid); i++ {
		track[i] = make([]bool, len(grid[0]))
	}
	cache := make(map[string]bool)
	move(grid, x, y, xdir, ydir, track, cache)
	sum := 0
	for _, row := range track {
		for _, c := range row {
			if c {
				sum++
			}
		}
	}
	return sum
}

func move(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	if x < 0 || y < 0 || y >= len(grid) || x >= len(grid[0]) {
		return
	}
	place := fmt.Sprintf("%v~%v~%v~%v", x, y, xdir, ydir)
	if cache[place] {
		return
	}
	cache[place] = true
	track[y][x] = true
	switch grid[y][x] {
	case '.':
		forward(grid, x, y, xdir, ydir, track, cache)
	case '/':
		forwardMirror(grid, x, y, xdir, ydir, track, cache)
	case '\\':
		backwardMirror(grid, x, y, xdir, ydir, track, cache)
	case '|':
		vertSplit(grid, x, y, xdir, ydir, track, cache)
	case '-':
		horSplit(grid, x, y, xdir, ydir, track, cache)
	}
}

func forward(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	move(grid, x+xdir, y+ydir, xdir, ydir, track, cache)
}

func forwardMirror(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	xdir, ydir = -ydir, -xdir
	move(grid, x+xdir, y+ydir, xdir, ydir, track, cache)
}

func backwardMirror(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	xdir, ydir = ydir, xdir
	move(grid, x+xdir, y+ydir, xdir, ydir, track, cache)
}

func vertSplit(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	if xdir == 0 {
		forward(grid, x, y, xdir, ydir, track, cache)
		return
	}
	move(grid, x, y-1, 0, -1, track, cache)
	move(grid, x, y+1, 0, 1, track, cache)
}

func horSplit(grid [][]byte, x, y, xdir, ydir int, track [][]bool, cache map[string]bool) {
	if ydir == 0 {
		forward(grid, x, y, xdir, ydir, track, cache)
		return
	}
	move(grid, x-1, y, -1, 0, track, cache)
	move(grid, x+1, y, 1, 0, track, cache)
}
