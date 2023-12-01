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

    words := []string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}

    for _, line := range lines {
        temp := ""
        first := int(^uint(0) >> 1)
        firstVal := 0
        last := 0
        lastVal := 0
        for i := 0; i < len(line); i++ {
            if (unicode.IsDigit(rune(line[i]))) {
                first = i
                firstVal, _ = strconv.Atoi(string(line[i]))
                break
            }
        }
        for i := len(line)-1; i >= 0; i-- {
            if (unicode.IsDigit(rune(line[i]))) {
                lastVal, _ = strconv.Atoi(string(line[i]))
                last = i
                break
            }
        }
        for i, word := range words {
            index := strings.Index(line, word)
            if index > -1 {
               if index < first {
                    first = index
                    firstVal = i + 1
               }
            }
            index = strings.LastIndex(line, word)
            if index > -1 {
                if index > last {
                    last = index
                    lastVal = i + 1
                }
            }
        }
        temp += fmt.Sprint(firstVal)
        temp += fmt.Sprint(lastVal)
        num, _ := strconv.Atoi(temp)
        fmt.Println(num)
        sum += num
    }
    fmt.Println(sum)
}
