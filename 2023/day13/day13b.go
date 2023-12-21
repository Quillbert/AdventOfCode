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
    outer:
	for _, group := range groups {
		lines := strings.Split(group, "\n")
        og := make([][]byte, len(lines))
        for t, line := range lines {
            og[t] = []byte(line)
        }
        orig := value(og, 0)
		for i := 0; i < len(lines); i++ {
			for j := 0; j < len(lines[0]); j++ {
				grid := make([][]byte, len(lines))
				for t, line := range lines {
					grid[t] = []byte(line)
				}
                if grid[i][j] == '.' {
                    grid[i][j] = '#'
                } else {
                    grid[i][j] = '.'
                }
                v := value(grid, orig)
                if v > 0 {
                    sum += v
                    fmt.Println(v)
                    fmt.Println(i, j)
                    continue outer
                }
			}
		}
	}
	fmt.Println(sum)
}

func value(grid [][]byte, orig int) int {
	sum := 0
row:
	for i := 1; i < len(grid); i++ {
		for j := 0; i-j-1 >= 0 && i+j < len(grid); j++ {
			for k := 0; k < len(grid[0]); k++ {
				if grid[i-j-1][k] != grid[i+j][k] {
					continue row
				}
			}
		}
        if 100 * i == orig {
            continue
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
        if i == orig {
            continue
        }
		sum += i
		break
	}
	return sum
}
