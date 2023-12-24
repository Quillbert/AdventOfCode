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
	count     int
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
	pq = append(pq, node{x: 0, y: 0, value: 0, priority: ex + ey, direction: -1, count: 0})
	for {
		curr := getMin(pq)
		pq = pq[1:]
		if curr.x == ex && curr.y == ey {
			fmt.Println(curr.value)
			break
		}
		loc := fmt.Sprintf("%v~%v~%v~%v", curr.x, curr.y, curr.direction, curr.count)
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
	if n.x > 0 {
		left := node{x: n.x - 1, y: n.y, value: n.value + grid[n.x-1][n.y], priority: n.value + grid[n.x-1][n.y] + (ex - (n.x - 1)) + (ey - n.y), direction: 3, count: 1}
		if n.direction == 3 {
			left.count = n.count + 1
		}
		if left.count < 4 && n.direction != 1 {
			out = append(out, left)
		}
	}
	if n.x < len(grid[0])-1 {
		right := node{x: n.x + 1, y: n.y, value: n.value + grid[n.x+1][n.y], priority: n.value + grid[n.x+1][n.y] + (ex - (n.x + 1)) + (ey - n.y), direction: 1, count: 1}
		if n.direction == 1 {
			right.count = n.count + 1
		}
		if right.count < 4 && n.direction != 3 {
			out = append(out, right)
		}
	}
	if n.y > 0 {
		up := node{x: n.x, y: n.y - 1, value: n.value + grid[n.x][n.y-1], priority: n.value + grid[n.x][n.y-1] + (ex - n.x) + (ey - (n.y - 1)), direction: 0, count: 1}
		if n.direction == 0 {
			up.count = n.count + 1
		}
		if up.count < 4 && n.direction != 2 {
			out = append(out, up)
		}
	}
	if n.y < len(grid)-1 {
		down := node{x: n.x, y: n.y + 1, value: n.value + grid[n.x][n.y+1], priority: n.value + grid[n.x][n.y+1] + (ex - n.x) + (ey - (n.y + 1)), direction: 2, count: 1}
		if n.direction == 2 {
			down.count = n.count + 1
		}
		if down.count < 4 && n.direction != 0 {
			out = append(out, down)
		}
	}
	return out
}
