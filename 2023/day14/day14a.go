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
	for k := len(grid) - 1; k > 0; k-- {
		for i := 0; i < k; i++ {
			for j := 0; j < len(grid[0]); j++ {
				if grid[i][j] == '.' && grid[i+1][j] == 'O' {
					grid[i][j] = grid[i+1][j]
					grid[i+1][j] = '.'
				}
			}
		}
	}
    count := 0
    for j := 0; j < len(grid[0]); j++ {
        for i := 0; i < len(grid); i++ {
            if grid[i][j] == 'O' {
                count += len(grid) - i
            }
        }
    }
	fmt.Println(count)
}
