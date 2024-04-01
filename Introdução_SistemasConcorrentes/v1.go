// Example code v1.go - Golang concurrency demonstration code
// By Cesar De Rose - 2021

package main

import (
    "fmt"
    "sync"
    "time"
)

var (
    mutex           sync.Mutex // mutex is used to define a critical section of code
    simulation_time int        = 0
)

func DoWork(TaskId, iterations int) {
    for i := 1; i <= iterations; i++ {
        mutex.Lock()
        fmt.Printf("%2d:", simulation_time)
        simulation_time++
        for s := 1; s <= TaskId*6; s++ {
            fmt.Printf(" ")
        }
        fmt.Printf("f%d\n", i) // working
        time.Sleep(1 * time.Second)
        mutex.Unlock()
    }
}

func main() {

    fmt.Println("\n  Beginning main goroutine")
    fmt.Println("\n        [T1]  [T2]  [T3]  [T4]\n")

    go DoWork(1, 4)
    go DoWork(2, 4)
    go DoWork(3, 4)
    go DoWork(4, 4)

    fmt.Println("\n  Hello from main goroutine")

    time.Sleep(16 * time.Second) // give the other goroutine time to finish

    fmt.Println("\n  Ending main goroutine \n")

    // At this point the program execution stops and all
    // active goroutines are killed.
}
