package main

import (
	"fmt"
	"os"
	"strings"
)

type node struct {
	left  string
	right string
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")
	pattern := lines[0]

	nodes := make(map[string]node)

	for _, line := range lines[2:] {
		parts := strings.Split(line, " = ")
		dirs := strings.Split(parts[1][1:len(parts[1])-1], ", ")
		nodes[parts[0]] = node{left: dirs[0], right: dirs[1]}
	}

	curr := "AAA"

	index := 0

	for curr != "ZZZ" {
		if pattern[index%len(pattern)] == 'L' {
			curr = nodes[curr].left
		} else {
			curr = nodes[curr].right
		}
		index++
	}

	fmt.Println(index)
}
