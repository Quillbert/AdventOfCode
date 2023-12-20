package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")

	sum := 0

	for i, line := range lines {
		parts := strings.Fields(line)
		g := make([]string, 0, 5)
		for i := 0; i < 5; i++ {
			g = append(g, parts[0])
		}
		parts[0] = strings.Join(g, "?")
		g = make([]string, 0, 5)
		for i := 0; i < 5; i++ {
			g = append(g, parts[1])
		}
		parts[1] = strings.Join(g, ",")
		pieces := toIntSlice(parts[1])
		maxes := maxes(pieces, len(parts[0]))
		spots := make([]int, len(pieces))
		fix(spots, pieces, maxes)
		count := 0
		for spots[0] <= maxes[0] {
			if check(spots, pieces, parts[0]) {
				count++
			}
			fix(spots, pieces, maxes)
		}
		sum += count
		fmt.Println(i)
	}
	fmt.Println(sum)

}

func count(line []byte) string {
	out := make([]string, 0)

	tally := 0

	for _, c := range line {
		if c == '#' {
			tally++
		} else if tally > 0 {
			out = append(out, fmt.Sprintf("%v", tally))
			tally = 0
		}
	}
	if tally > 0 {
		out = append(out, fmt.Sprintf("%v", tally))
	}

	return strings.Join(out, ",")
}

func toIntSlice(input string) []int {
	out := make([]int, 0)
	for _, v := range strings.Split(input, ",") {
		num, _ := strconv.Atoi(v)
		out = append(out, num)
	}
	return out
}

func maxes(pieces []int, length int) []int {
	out := make([]int, len(pieces))
	running := length
	for i := len(pieces) - 1; i >= 0; i-- {
		out[i] = running - pieces[i]
		running -= pieces[i]
		running--
	}
	return out
}

func fix(spots, dists, maxes []int) {
	for i := 1; i < len(spots); i++ {
		if spots[i] < spots[i-1]+dists[i-1]+1 {
			spots[i] = spots[i-1] + dists[i-1] + 1
		}
	}
	bad := false
	for i := len(spots) - 1; i > 0; i-- {
		if spots[i] > maxes[i] {
			bad = true
			spots[i-1]++
			for j := i; j < len(spots); j++ {
				spots[j] = 0
			}
		}
	}
	if bad && spots[0] <= maxes[0] {
		fix(spots, dists, maxes)
	}
}

func check(spots, dists []int, data string) bool {
	for i := 0; i < len(spots); i++ {
		if strings.Contains(data[spots[i]:spots[i]+dists[i]], ".") {
			spots[i]++
			for j := i + 1; j < len(spots); j++ {
				spots[j] = 0
			}
			return false
		}
	}
	if strings.Contains(data[0:spots[0]], "#") {
		spots[0] = len(data) + 1
		return false
	}
	for i := 0; i < len(spots)-1; i++ {
		if strings.Contains(data[spots[i]+dists[i]:spots[i+1]], "#") {
			spots[i]++
			for j := i + 1; j < len(spots); j++ {
				spots[j] = 0
			}
			return false
		}
	}
	if strings.Contains(data[spots[len(spots)-1]+dists[len(dists)-1]:len(data)], "#") {
		spots[len(spots)-1]++
		return false
	}
	spots[len(spots)-1]++
	return true
}
