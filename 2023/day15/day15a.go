package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.TrimSpace(string(content))
	parts := strings.Split(input, ",")
	sum := 0
	for _, part := range parts {
		value := 0
		for _, c := range part {
			value += int(c)
			value *= 17
			value %= 256
		}
		sum += value
	}
	fmt.Println(sum)
}
