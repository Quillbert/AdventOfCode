package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type node struct {
	x         int
	y         int
	value     int
	priority  int
	direction int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")

	grid := make([][]int, len(lines))
	for i, line := range lines {
		grid[i] = make([]int, len(line))
		for j, c := range line {
			num, _ := strconv.Atoi(string(c))
			grid[i][j] = num
		}
	}
	ex := len(grid[0]) - 1
	ey := len(grid) - 1
	visited := make(map[string]bool)
	pq := make([]node, 0)
	pq = append(pq, node{x: 0, y: 0, value: 0, priority: ex + ey, direction: -1})
	for {
		curr := getMin(pq)
		pq = pq[1:]
		if curr.x == ex && curr.y == ey {
			fmt.Println(curr.value)
			break
		}
		loc := fmt.Sprintf("%v~%v~%v", curr.x, curr.y, curr.direction)
		if visited[loc] {
			continue
		}
		visited[loc] = true
		neigh := neighbors(curr, grid)
		pq = append(pq, neigh...)
	}
}

func getMin(pq []node) node {
	min := 0
	for i, node := range pq {
		if node.priority < pq[min].priority {
			min = i
		}
	}
	pq[0], pq[min] = pq[min], pq[0]
	return pq[0]
}

func neighbors(n node, grid [][]int) []node {
	out := make([]node, 0)
	ex := len(grid[0]) - 1
	ey := len(grid) - 1
	for i := 4; i <= 10; i++ {
		if n.x >= i {
            sum := 0
            for j := 1; j <= i; j++ {
                sum += grid[n.y][n.x-j]
            }
			left := node{x: n.x - i, y: n.y, value: n.value + sum, priority: n.value + sum + (ex - (n.x - i)) + (ey - n.y), direction: 3}
			if n.direction != 1 && n.direction != 3 {
				out = append(out, left)
			}
		}
		if n.x < len(grid[0])-i {
            sum := 0
            for j := 1; j <= i; j++ {
                sum += grid[n.y][n.x+j]
            }
			right := node{x: n.x + i, y: n.y, value: n.value + sum, priority: n.value + sum + (ex - (n.x + i)) + (ey - n.y), direction: 1}
			if n.direction != 3 && n.direction != 1 {
				out = append(out, right)
			}
		}
		if n.y > i {
            sum := 0
            for j := 1; j <= i; j++ {
                sum += grid[n.y-j][n.x]
            }
			up := node{x: n.x, y: n.y - i, value: n.value + sum, priority: n.value + sum + (ex - n.x) + (ey - (n.y - i)), direction: 0}
			if n.direction != 2 && n.direction != 2 {
				out = append(out, up)
			}
		}
		if n.y < len(grid)-i {
            sum := 0
            for j := 1; j <= i; j++ {
                sum += grid[n.y+j][n.x]
            }
			down := node{x: n.x, y: n.y + i, value: n.value + sum, priority: n.value + sum + (ex - n.x) + (ey - (n.y + i)), direction: 2}
			if n.direction != 0 && n.direction != 2 {
				out = append(out, down)
			}
		}
	}
	return out
}
