package main

import (
    "fmt"
    "os"
    "slices"
    "strconv"
    "strings"
)

type ticket struct {
    hand string
    bet int
}

func main() {
    content, _ := os.ReadFile("input.txt")
    input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
    lines := strings.Split(input, "\n")

    tickets := make([]ticket, 0, len(lines))

    for _, line := range lines {
        parts := strings.Fields(line)
        bet, _ := strconv.Atoi(parts[1])
        tik := ticket{hand: parts[0], bet: bet}
        tickets = append(tickets, tik)
    }


    slices.SortFunc(tickets, ticketCmp)

    out := 0

    for i, ticket := range tickets {
        out += (i + 1) * ticket.bet 
    }

    fmt.Println(out)
}

func ticketCmp(a, b ticket) int {
   aCards := make(map[byte]int) 
   bCards := make(map[byte]int)

   for i := 0; i < 5; i++ {
       aCards[a.hand[i]]++
       bCards[b.hand[i]]++
   }

   aCounts := make(map[int]int)
   bCounts := make(map[int]int)

   aCounts[0] = 1
   bCounts[0] = 1

   for k, v := range aCards {
       if k == 'J' {
           continue
       }
       aCounts[v]++
   }

   for k, v := range bCards {
       if k == 'J' {
           continue
       }
       bCounts[v]++
   }

   for i := 5; i >= 0; i-- {
        if aCounts[i] > 0 {
            aCounts[i]--
            aCounts[i + aCards['J']]++
            break
        }
   }

   for i := 5; i >= 0; i-- {
        if bCounts[i] > 0 {
            bCounts[i]--
            bCounts[i + bCards['J']]++
            break
        }
   }

   for i := 5; i > 0; i-- {
       if aCounts[i] != bCounts[i] {
           return aCounts[i] - bCounts[i];
       }
   }

   ranks := map[byte]int {
       '2': 1,
       '3': 2,
       '4': 3,
       '5': 4,
       '6': 5,
       '7': 6,
       '8': 7,
       '9': 8,
       'T': 9,
       'J': 0,
       'Q': 11,
       'K': 12,
       'A': 13,
   }

   for i := 0; i < 5; i++ {
       if a.hand[i] != b.hand[i] {
           return ranks[a.hand[i]] - ranks[b.hand[i]]
       }
   }

   return 0
}
