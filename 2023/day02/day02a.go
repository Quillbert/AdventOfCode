package main

import (
    "fmt"
    "os"
    "regexp"
    "strconv"
    "strings"
)

func main() {
    content, _ := os.ReadFile("input.txt")
    input := string(content)
    lines := strings.Split(input, "\n")

    regex := regexp.MustCompile("[,;]")

    sum := 0
    for i, line := range lines {
        if len(line) == 0 {
            break
        }
        m := make(map[string]int)
        cubes := regex.Split(strings.Split(line, ": ")[1], -1)
        for _, cube := range cubes {
            stuff := strings.Split(strings.TrimSpace(cube), " ")
            num, _ := strconv.Atoi(stuff[0])
            color := stuff[1]
            if num > m[color] {
                m[color] = num
            }
        }
        if (m["red"] <= 12 && m["green"] <= 13 && m["blue"] <= 14) {
            sum += i + 1
        }
    }

    fmt.Println(sum)
}
