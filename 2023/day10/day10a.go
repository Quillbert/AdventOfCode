package main

import (
	"fmt"
	"os"
	"slices"
	"strings"
)

type node struct {
	val  string
	dist int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")

	n := make(map[string][]string)

	var start string

	for i, line := range strings.Split(input, "\n") {
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

	fmt.Println(bfs(n, start))
}

func bfs(n map[string][]string, start string) int {
	init := node{val: start, dist: 0}
	queue := make([]node, 0)
	visited := make(map[string]bool)
	queue = append(queue, init)
	visited[start] = true
	max := 0
	for len(queue) > 0 && len(visited) < len(n) {
		curr := queue[0]
		queue = queue[1:]
		if curr.dist > max {
			max = curr.dist
		}
		for _, neigh := range n[curr.val] {
			if !visited[neigh] {
				newNode := node{val: neigh, dist: curr.dist + 1}
				queue = append(queue, newNode)
				visited[neigh] = true
			}
		}
	}
	return max
}
