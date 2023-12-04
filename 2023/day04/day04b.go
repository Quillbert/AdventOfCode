package main

import (
    "fmt"
    "os"
    "strconv"
    "strings"
)

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.TrimSpace(string(content))
    lines := strings.Split(strings.ReplaceAll(input, "\r\n", "\n"), "\n")

    sum := 0

    cardCounts := make(map[int]int)

    for i, line := range lines {
        cardCounts[i]++
        parts := strings.Split(line, ": ")
        line = parts[1]
        parts = strings.Split(line, " | ")
        nums := strings.Fields(parts[0])
        winners := make(map[int]bool)
        for _, num := range nums {
            n, _ := strconv.Atoi(num)
            winners[n] = true
        }
        nums = strings.Fields(parts[1])
        haves := make(map[int]bool)
        for _, num := range nums {
            n, _ := strconv.Atoi(num)
            haves[n] = true
        }
        count := 1
        for k, _ := range winners {
            if haves[k] {
                cardCounts[i + count] += cardCounts[i]
                count++
            }
        }
    }

    for _, val := range cardCounts {
        sum += val
    }

    fmt.Println(sum)
}
