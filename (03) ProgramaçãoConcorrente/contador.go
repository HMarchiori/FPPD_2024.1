package main

import (
	"fmt"
	"sync"
)

const COUNT = 1000000

var counter int
var mu sync.Mutex
var wg sync.WaitGroup

func increment(id int) {
	fmt.Println("Iniciando goroutine", id)
	for i := 0; i < COUNT; i++ {
		mu.Lock()
		counter++
		mu.Unlock()
	}
	wg.Done()
}

func main() {
	const NUM_THREADS = 10

	for i := 0; i < NUM_THREADS; i++ {
		wg.Add(1)
		fmt.Println("Criando thread", i)
		go increment(i)
	}

	wg.Wait()
	fmt.Println("Resultado final:", counter)
}

