package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	groups := strings.Split(input, "\n\n")

	sum := 0
	for _, group := range groups {
		lines := strings.Split(group, "\n")
		grid := make([][]byte, len(lines))
		for i, line := range lines {
			grid[i] = []byte(line)
		}

	row:
		for i := 1; i < len(grid); i++ {
			for j := 0; i-j-1 >= 0 && i+j < len(grid); j++ {
				for k := 0; k < len(grid[0]); k++ {
					if grid[i-j-1][k] != grid[i+j][k] {
						continue row
					}
				}
			}
			sum += 100 * i
			break
		}

	column:
		for i := 1; i < len(grid[0]); i++ {
			for j := 0; i-j-1 >= 0 && i+j < len(grid[0]); j++ {
				for k := 0; k < len(grid); k++ {
					if grid[k][i-j-1] != grid[k][i+j] {
						continue column
					}
				}
			}
			sum += i
			break
		}

	}
	fmt.Println(sum)
}
