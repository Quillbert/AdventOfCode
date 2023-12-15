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

type set struct {
	from   int
	length int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")

	groups := strings.Split(input, "\n\n")

	seedStrings := strings.Split(strings.Split(strings.TrimSpace(groups[0]), ": ")[1], " ")

	seeds := make([]set, 0, len(seedStrings)/2)

	for i := 0; i < len(seedStrings); i += 2 {
		from, _ := strconv.Atoi(seedStrings[i])
		length, _ := strconv.Atoi(seedStrings[i+1])
		seeds = append(seeds, set{from: from, length: length})
	}

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

	for _, conv := range maps {
        fmt.Println(seeds)
		newSeeds := make([]set, 0)
	LABEL:
		for i := 0; i < len(seeds); i++ {
			for _, mapping := range conv {
				if intersect(seeds[i], mapping) {
					if seeds[i].from < mapping.from {
						newSeed := set{from: seeds[i].from, length: mapping.from - seeds[i].from}
						seeds = append(seeds, newSeed)
					}
					if seeds[i].from+seeds[i].length > mapping.from+mapping.length {
						newSeed := set{from: mapping.from + mapping.length,
							length: seeds[i].from + seeds[i].length - (mapping.from + mapping.length)}
						seeds = append(seeds, newSeed)
					}
					newSeed := set{from: Max(seeds[i].from, mapping.from),
						length: Min(seeds[i].from+seeds[i].length, mapping.from+mapping.length) -
							Max(seeds[i].from, mapping.from)}
                    newSeed.from -= mapping.from - mapping.to
					newSeeds = append(newSeeds, newSeed)
					continue LABEL
				}
			}
			newSeeds = append(newSeeds, seeds[i])
		}
		seeds = newSeeds
	}

    fmt.Println(seeds)

	for _, seed := range seeds {
		if seed.from < min {
			min = seed.from
		}
	}

	fmt.Println(min)

}

func intersect(r set, p mapping) bool {
	right := r.from < p.from+p.length
	left := r.from+r.length > p.from
	return left && right
}

func Max(a int, b int) int {
	if a > b {
		return a
	}
	return b
}

func Min(a int, b int) int {
	if a < b {
		return a
	}
	return b
}
