package main

import (
    "fmt"
    "os"
    "strings"
)

func main() {
    content, _ := os.ReadFile("input.txt")    
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
    lines := strings.Split(input, "\n")

    sum := 0

    for _, line := range lines {
        parts := strings.Fields(line)
        qCount := strings.Count(parts[0], "?")
        ceil := 1 << qCount
        fmtString := fmt.Sprintf("%%0%vb", qCount)
        c := 0
        for val := 0; val < ceil; val++ {
            bin := fmt.Sprintf(fmtString, val)
            spot := 0
            working := []byte(parts[0])
            for i := 0; i < len(working); i++ {
                if working[i] == '?' {
                    if bin[spot] == '0' {
                        working[i] = '.'
                    } else {
                        working[i] = '#'
                    }
                    spot++
                }
            }
            if count(working) == parts[1] {
                c++
            }
        }
        sum += c
        fmt.Println(c)
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
