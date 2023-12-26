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

    sections := strings.Split(input, "\n\n")

    workflows := make(map[string][]string)

    for _, line := range strings.Split(sections[0], "\n") {
        parts := strings.Split(line, "{")
        workflows[parts[0]] = strings.Split(parts[1][:len(parts[1])-1], ",")
    }

    sum := 0

    for _, line := range strings.Split(sections[1], "\n") {
        parts := strings.Split(line[1:len(line)-1], ",")
        part := make(map[string]int)
        for _, party := range parts {
            p := strings.Split(party, "=")
            num, _ := strconv.Atoi(p[1])
            part[p[0]] = num
        }
        curr := "in"
        for curr != "A" && curr != "R" {
            curr = process(part, workflows[curr])
        }
        if curr == "A" {
            for _, v := range part {
                sum += v
            }
        }
    }

    fmt.Println(sum)
}

func process(part map[string]int, workflow []string) string {
    for _, rule := range workflow {
        if !strings.Contains(rule, ":") {
            return rule
        }
        parts := strings.Split(rule, ":")
        num, _ := strconv.Atoi(parts[0][2:])
        switch parts[0][1] {
        case '>':
            if part[parts[0][0:1]] > num {
                return parts[1]
            }
        case '<':
            if part[parts[0][0:1]] < num {
                return parts[1]
            }
        }
    }
    return ""
}
