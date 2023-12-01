package main

import (
    "fmt"
    "os"
    "strings"
    "strconv"
    "unicode"
)

func main() {
    content, _ := os.ReadFile("input.txt")
    input := string(content)
    lines := strings.Split(input, "\n")
    sum := 0
    for _, line := range lines {
        temp := ""
        for i := 0; i < len(line); i++ {
            if (unicode.IsDigit(rune(line[i]))) {
                temp += string(line[i])
                break
            }
        }
        for i := len(line)-1; i >= 0; i-- {
            if (unicode.IsDigit(rune(line[i]))) {
                temp += string(line[i])
                break
            }
        }
        num, _ := strconv.Atoi(temp)
        sum += num
    }
    fmt.Println(sum)
}
