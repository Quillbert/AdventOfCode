package main

import (
    "fmt"
    "math"
    "os"
    "strconv"
    "strings"
)

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
    lines := strings.Split(input, "\n")

    times := strings.Fields(lines[0])
    distances := strings.Fields(lines[1])

    out := 1

    for i := 1; i < len(times); i++ {
        time, _ := strconv.Atoi(times[i])
        distance, _ := strconv.Atoi(distances[i])
        det := math.Sqrt(float64(time * time - 4 * distance))
        bottom := math.Floor((float64(time) - det)/2 + 1)
        top := math.Ceil((float64(time) + det)/2 - 1)
        count := top - bottom + 1
        out *= int(count)

    }

    fmt.Println(out)
}
