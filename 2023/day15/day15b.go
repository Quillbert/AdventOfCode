package main

import (
	"fmt"
	"os"
    "strconv"
	"strings"
)

type entry struct {
    key string
    value int
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.TrimSpace(string(content))
	parts := strings.Split(input, ",")
    table := make([][]entry, 256)
    for i := 0; i < len(table); i++ {
        table[i] = make([]entry, 0)
    }
	for _, part := range parts {
        if strings.Contains(part, "-") {
            remove(table, part[:len(part)-1])
        } else {
            p := strings.Split(part, "=")
            num, _ := strconv.Atoi(p[1])
            add(table, p[0], num)
        }
	}
    sum := 0
    for i, box := range table {
        for j, e := range box {
            sum += (i + 1) * (j + 1) * e.value
        }
    }
    fmt.Println(sum)
}

func add(table [][]entry, key string, value int) {
    index := hash(key)
    for i := 0; i < len(table[index]); i++ {
        if table[index][i].key == key {
            table[index][i].value = value
            return
        }
    }
    e := entry{key: key, value: value}
    table[index] = append(table[index], e)
}

func remove(table [][]entry, key string) {
    index := hash(key)
    for i := 0; i < len(table[index]); i++ {
        if table[index][i].key == key {
            table[index] = append(table[index][:i], table[index][i+1:]...)
        }
    }
}

func hash(part string) int {
	value := 0
	for _, c := range part {
		value += int(c)
		value *= 17
		value %= 256
	}
	return value
}
