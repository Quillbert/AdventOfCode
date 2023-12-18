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

	curr := make([]string, 0)

	for k, _ := range nodes {
		if k[2] == 'A' {
			curr = append(curr, k)
		}
	}

	indices := make([]int, 0)

	for i := 0; i < len(curr); i++ {
		index := 0

		done := false

		for !done {
			done = true
			if pattern[index%len(pattern)] == 'L' {
				curr[i] = nodes[curr[i]].left
			} else {
				curr[i] = nodes[curr[i]].right
			}

			val := curr[i]
			if val[2] != 'Z' {
				done = false
			}

			index++
		}
        indices = append(indices, index)
	}

    out := 1

    for _, v := range indices {
        out = lcm(out, v)
    }

    fmt.Println(out)
}

func gcd(a, b int) int {
    if b == 0 {
        return a
    }
    return gcd(b, a % b)
}

func lcm(a, b int) int {
    return a * b / gcd(a, b)
}
