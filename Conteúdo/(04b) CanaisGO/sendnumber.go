package main

import (
	"fmt"
	"time"
)

// Função que simula o envio de um número após algum cálculo ou operação.
// Ela envia um número para o canal fornecido.
func sendNumber(ch chan<- int, num int) {
	// Simula um cálculo ou operação que leva 2 segundos.
	time.Sleep(2 * time.Second)
	ch <- num // Envia 'num' através do canal.
}

func main() {
	// Cria um canal de inteiros.
	numChannel := make(chan int)

	// Inicia a goroutine sendNumber para enviar um número através do canal.
	go sendNumber(numChannel, 42)

	// Recebe o número enviado para o canal e armazena na variável 'num'.
	num := <-numChannel

	// Imprime o número recebido.
	fmt.Println("Recebi o número:", num)
}
