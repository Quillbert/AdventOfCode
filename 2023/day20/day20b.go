package main

import (
	"fmt"
	"os"
	"strings"
)

type flipflop struct {
	out   []string
	state bool
}

type conjunction struct {
	out    []string
	states map[string]bool
}

type signal struct {
	from  string
	to    string
	state bool
}

func main() {
	content, _ := os.ReadFile("input.txt")
	input := strings.ReplaceAll(strings.TrimSpace(string(content)), "\r\n", "\n")
	lines := strings.Split(input, "\n")

	basic := make(map[string][]string)
	flipflops := make(map[string]flipflop)
	conjunctions := make(map[string]conjunction)

	for _, line := range lines {
		parts := strings.Split(line, " -> ")
		out := strings.Split(parts[1], ", ")
		switch parts[0][0] {
		case '%':
			flipflops[parts[0][1:]] = flipflop{out: out, state: false}
		case '&':
			conjunctions[parts[0][1:]] = conjunction{out: out, states: make(map[string]bool)}
		default:
			basic[parts[0]] = out
		}
	}

	for _, line := range lines {
		parts := strings.Split(line, " -> ")
		out := strings.Split(parts[1], ", ")
		if parts[0][0] == '%' || parts[0][0] == '&' {
			parts[0] = parts[0][1:]
		}
		for _, v := range out {
			if _, i := conjunctions[v]; i {
				conjunctions[v].states[parts[0]] = false
			}
		}
	}

	signals := make([]signal, 0)

	lowCount, highCount := 0, 0


	for i := 0; ; i++ {
	    signals = append(signals, signal{from: "button", to: "broadcaster", state: false})
	outer:
		for len(signals) > 0 {
			s := signals[0]
			signals = signals[1:]
			if s.state {
				highCount++
			} else {
				lowCount++
			}
			if out, con := basic[s.to]; con {
				for _, o := range out {
					signals = append(signals, signal{from: s.to, to: o, state: s.state})
				}
			} else if entry, con := flipflops[s.to]; con {
				if !s.state {
					entry.state = !entry.state
					flipflops[s.to] = entry
					for _, o := range flipflops[s.to].out {
						signals = append(signals, signal{from: s.to, to: o, state: flipflops[s.to].state})
					}
				}
			} else if entry, con := conjunctions[s.to]; con {
				entry.states[s.from] = s.state
				conjunctions[s.to] = entry
				for _, v := range entry.states {
					if !v {
						for _, o := range entry.out {
							signals = append(signals, signal{from: s.to, to: o, state: true})
						}
						continue outer
					}
				}
				for _, o := range entry.out {
					signals = append(signals, signal{from: s.to, to: o, state: false})
				}
                if s.to == "mq" || s.to == "tg" || s.to == "xf" || s.to == "tz" {
                    fmt.Printf("%v: %v\n", s.to, i + 1)
                }

			}
		}
	}

	fmt.Println(lowCount * highCount)
}
