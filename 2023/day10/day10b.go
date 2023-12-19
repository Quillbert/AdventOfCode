package main

import (
	"fmt"
	"os"
	"slices"
	"strconv"
	"strings"
)

type node struct {
	val  string
	dist int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")

	n := make(map[string][]string)

	var start string

	for i, line := range lines {
		for j, c := range line {
			loc := fmt.Sprintf("%v,%v", j, i)
			n[loc] = make([]string, 0)
			left := fmt.Sprintf("%v,%v", j-1, i)
			right := fmt.Sprintf("%v,%v", j+1, i)
			up := fmt.Sprintf("%v,%v", j, i-1)
			down := fmt.Sprintf("%v,%v", j, i+1)
			switch c {
			case '|':
				n[loc] = append(n[loc], up, down)
			case '-':
				n[loc] = append(n[loc], left, right)
			case 'L':
				n[loc] = append(n[loc], up, right)
			case 'J':
				n[loc] = append(n[loc], up, left)
			case '7':
				n[loc] = append(n[loc], down, left)
			case 'F':
				n[loc] = append(n[loc], down, right)
			case 'S':
				start = loc
			}
		}
	}

	for k, v := range n {
		if slices.Contains(v, start) {
			n[start] = append(n[start], k)
		}
	}

	path := bfs(n, start)

	grid := make([][]byte, 2*len(lines))
	for i := 0; i < len(grid); i++ {
		grid[i] = make([]byte, 2*len(lines[0]))
	}

	for k, _ := range path {
		coords := strings.Split(k, ",")
		x, _ := strconv.Atoi(coords[0])
		y, _ := strconv.Atoi(coords[1])
		grid[2*y][2*x] = 1
	}

	for i := 0; i < len(grid)-1; i++ {
		for j := 0; j < len(grid[0])-1; j++ {
			if i%2 == 0 && j%2 == 0 {
				continue
			}
			if i%2 == 0 {
				left := fmt.Sprintf("%v,%v", j/2, i/2)
				right := fmt.Sprintf("%v,%v", (j+1)/2, i/2)
				if path[left] && path[right] && slices.Contains(n[left], right) {
					grid[i][j] = 1
				}
			}
			if j%2 == 0 {
				up := fmt.Sprintf("%v,%v", j/2, i/2)
				down := fmt.Sprintf("%v,%v", j/2, (i+1)/2)
				if path[up] && path[down] && slices.Contains(n[up], down) {
					grid[i][j] = 1
				}
			}
		}
	}

    count := byte(2)

    for i := 0; i < len(grid); i++ {
        for j := 0; j < len(grid[i]); j++ {
            if grid[i][j] == 0 {
                floodfill(grid, j, i, count)
                count++
            }
        }
    }

    counts := make(map[byte]int)

    for i := 0; i < len(lines); i++ {
        for j := 0; j < len(lines[0]); j++ {
            counts[grid[i*2][j*2]]++
        }
    }
    counts[1] = 0

    for i := 0; i < len(grid[0]); i++ {
        counts[grid[0][i]] = 0
        counts[grid[len(grid)-1][i]] = 0
    }

    for i := 0; i < len(grid); i++ {
        counts[grid[i][0]] = 0
        counts[grid[i][len(grid[0])-1]] = 0
    }

    max := 0

    for _, v := range counts {
        if v > max {
            max = v
        }
    }

    fmt.Println(max)
}

func bfs(n map[string][]string, start string) map[string]bool {
	init := node{val: start, dist: 0}
	queue := make([]node, 0)
	visited := make(map[string]bool)
	queue = append(queue, init)
	visited[start] = true
	for len(queue) > 0 {
		curr := queue[0]
		queue = queue[1:]
		for _, neigh := range n[curr.val] {
			if !visited[neigh] {
				newNode := node{val: neigh, dist: curr.dist + 1}
				queue = append(queue, newNode)
				visited[neigh] = true
			}
		}
	}
	return visited
}

func floodfill(grid [][]byte, x, y int, v byte) {
    if x < 0 || y < 0 || y >= len(grid) || x >= len(grid[y]) {
        return
    }
    if grid[y][x] == 1 || grid[y][x] == v {
        return
    }
    grid[y][x] = v
    floodfill(grid, x - 1, y, v)
    floodfill(grid, x + 1, y, v)
    floodfill(grid, x, y - 1, v)
    floodfill(grid, x, y + 1, v)
}
