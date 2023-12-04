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

    for _, line := range lines {
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
        count := 0
        for k, _ := range winners {
            if haves[k] {
                if count == 0 {
                    count = 1
                } else {
                    count *= 2
                }
            }
        }
        sum += count
    }

    fmt.Println(sum)
}
