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
    cache := make(map[string]int)
    cache[gridToString(grid)] = 0
    spot := 0
    for {
        spinCycle(grid)
        spot++
        s := gridToString(grid)
        _, ok := cache[s]
        if ok {
            break
        }
        cache[s] = spot
    }
    old := cache[gridToString(grid)]
    cycle := spot - old
    for spot + cycle < 1_000_000_000 {
        spot += cycle
    }
    for spot < 1_000_000_000 {
        spinCycle(grid)
        spot++
    }
    fmt.Println(spot)
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

func spinCycle(grid [][]byte) {
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
 	for k := len(grid[0]) - 1; k > 0; k-- {
		for i := 0; i < len(grid); i++ {
			for j := 0; j < k; j++ {
				if grid[i][j] == '.' && grid[i][j+1] == 'O' {
					grid[i][j] = grid[i][j+1]
					grid[i][j+1] = '.'
				}
			}
		}
	}
 	for k := 0; k < len(grid) - 1; k++ {
		for i := len(grid) - 1; i > k; i-- {
			for j := 0; j < len(grid[0]); j++ {
				if grid[i][j] == '.' && grid[i-1][j] == 'O' {
					grid[i][j] = grid[i-1][j]
					grid[i-1][j] = '.'
				}
			}
		}
	}
 	for k := 0; k < len(grid[0]) - 1; k++ {
		for i := 0; i < len(grid); i++ {
			for j := len(grid[0]) - 1; j > k; j-- {
				if grid[i][j] == '.' && grid[i][j-1] == 'O' {
					grid[i][j] = grid[i][j-1]
					grid[i][j-1] = '.'
				}
			}
		}
	}
}

func gridToString(grid [][]byte) string {
    lines := make([]string, len(grid))
    for i, line := range grid {
        lines[i] = string(line)
    }
    return strings.Join(lines, "\n")
}
