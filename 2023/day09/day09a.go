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
    lines := strings.Split(input, "\n")

    sum := 0

    for _, line := range lines {
        parts := strings.Fields(line)
        nums := make([]int, 0, len(parts))
        for _, num := range parts {
            n, _ := strconv.Atoi(num)
            nums = append(nums, n)
        }
        sum += nextNum(nums) + nums[len(nums)-1]
    }

    fmt.Println(sum)
}

func nextNum(nums []int) int {
    differences := make([]int, 0, len(nums) - 1)
    for i := 0; i < len(nums) - 1; i++ {
        differences = append(differences, nums[i+1] - nums[i])
    }
    zeroes := true
    for _, v := range differences {
        if v != 0 {
            zeroes = false
            break
        }
    }
    if zeroes {
        return 0
    }
    next := nextNum(differences)

    return differences[len(differences)-1] + next
}
