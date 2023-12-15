package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

type mapping struct {
	to     int
	from   int
	length int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")

	groups := strings.Split(input, "\n\n")

	seedStrings := strings.Split(strings.Split(strings.TrimSpace(groups[0]), ": ")[1], " ")

	fmt.Println(seedStrings)

	maps := make([][]mapping, 0, len(groups)-1)

	for _, group := range groups[1:] {
		lines := strings.Split(group, "\n")
		maps = append(maps, make([]mapping, len(lines)-1))
		for i, line := range lines[1:] {
			nums := strings.Split(line, " ")
			ints := make([]int, 0, 3)
			for _, num := range nums {
				n, _ := strconv.Atoi(num)
				ints = append(ints, n)
			}
			maps[len(maps)-1][i] = mapping{from: ints[1], to: ints[0], length: ints[2]}
		}
	}

	min := math.MaxInt

	for _, seed := range seedStrings {
		s, _ := strconv.Atoi(seed)
		for _, conv := range maps {
			for _, mapping := range conv {
				if s >= mapping.from && s < mapping.from+mapping.length {
					s -= mapping.from - mapping.to
					break
				}
			}
		}
		if s < min {
			min = s
		}
	}

	fmt.Println(min)

}
