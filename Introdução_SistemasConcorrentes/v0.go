// Example code v0.go - Golang sequential demonstration code
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

    DoWork(1, 4)
    DoWork(2, 4)
    DoWork(3, 4)
    DoWork(4, 4)

    fmt.Println("\n  Hello from main goroutine")
    fmt.Println("\n  Ending main goroutine \n")
}
