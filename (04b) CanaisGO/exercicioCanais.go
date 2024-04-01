package main

import (
	"fmt"
	"time"
)

func incrementa(ch chan<- bool) {
	for i := 1; i <= 10; i++ {
		fmt.Println(i)
		time.Sleep(1 * time.Second)
	}
	ch <- true // Sinaliza a conclusão
}

func decrementa(ch chan<- bool) {
	for i := 10; i > 0; i-- {
		fmt.Println(i)
		time.Sleep(1 * time.Second)
	}
	ch <- true // Sinaliza a conclusão
}

func main() {
	ch := make(chan bool, 2) // Canal bufferizado para esperar 2 sinais de conclusão

	go incrementa(ch)   // Dispara a goroutine para contar de 1 a 10
	go decrementa(ch) // Dispara a goroutine para contar de 10 a 1

	// Espera receber dois sinais de conclusão
	<-ch
	<-ch

	fmt.Println("Ambas as goroutines terminaram sua execução.")
}
