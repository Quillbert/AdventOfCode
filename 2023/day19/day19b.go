package main

import (
    "fmt"
    "os"
    "strconv"
    "strings"
)

type r struct {
    low int
    high int
}

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")

    sections := strings.Split(input, "\n\n")

    workflows := make(map[string][]string)

    for _, line := range strings.Split(sections[0], "\n") {
        parts := strings.Split(line, "{")
        workflows[parts[0]] = strings.Split(parts[1][:len(parts[1])-1], ",")
    }

    parts := make([]map[string]r, 0)

    start := make(map[string]r)
    start["x"] = r{low: 1, high: 4000}
    start["m"] = r{low: 1, high: 4000}
    start["a"] = r{low: 1, high: 4000}
    start["s"] = r{low: 1, high: 4000}

    sum := 0

    parts = append(parts, start)
    
    for len(parts) > 0 {
        part := parts[0]
        parts = parts[1:]
        curr := "in"
        for curr != "A" && curr != "R" && curr != "" {
            curr = process(part, workflows[curr], &parts)
        }
        if curr == "A" {
            c := 1
            for _, v := range part {
                c *= (v.high - v.low) + 1
            }
            sum += c
        }
    }

    fmt.Println(sum)

}

func process(part map[string]r, workflow []string, list *[]map[string]r) string {
    for _, rule := range workflow {
        if !strings.Contains(rule, ":") {
            return rule
        }
        parts := strings.Split(rule, ":")
        num, _ := strconv.Atoi(parts[0][2:])
        switch parts[0][1] {
        case '>':
            if part[parts[0][0:1]].low > num {
                return parts[1]
            } else if part[parts[0][0:1]].high > num {
                a := make(map[string]r)
                b := make(map[string]r)
                a[parts[0][0:1]] = r{low: part[parts[0][0:1]].low, high: num}
                b[parts[0][0:1]] = r{high: part[parts[0][0:1]].high, low: num + 1}
                for k, v := range part {
                    if k != parts[0][0:1] {
                        a[k] = v
                        b[k] = v
                    }
                }
                *list = append(*list, a, b)
                return ""
            }
        case '<':
            if part[parts[0][0:1]].high < num {
                return parts[1]
            } else if part[parts[0][0:1]].low < num {
                a := make(map[string]r)
                b := make(map[string]r)
                a[parts[0][0:1]] = r{low: part[parts[0][0:1]].low, high: num - 1}
                b[parts[0][0:1]] = r{high: part[parts[0][0:1]].high, low: num}
                for k, v := range part {
                    if k != parts[0][0:1] {
                        a[k] = v
                        b[k] = v
                    }
                }
                *list = append(*list, a, b)
                return ""
            }
        }
    }
    return ""
}
