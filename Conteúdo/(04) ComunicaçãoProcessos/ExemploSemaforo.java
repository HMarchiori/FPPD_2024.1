import java.util.concurrent.Semaphore;

public class ExemploSemaforo {

    // Cria um semáforo com 2 permissões
    private static final Semaphore semaforo = new Semaphore(2, true); // true para FIFO (First In, First Out

    static class Task extends Thread {
        private String nome;

        public Task(String nome) {
            this.nome = nome;
        }

        public void run() {
            try {
                // Adquire uma permissão do semáforo
                System.out.println(nome + " quer acessar o recurso.");
                semaforo.acquire();
                System.out.println(nome + " está acessando o recurso.");

                // Simula o tempo que a thread passa usando o recurso
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Libera a permissão do semáforo
                System.out.println(nome + " terminou de usar o recurso.");
                semaforo.release();
            }
        }
    }

    public static void main(String[] args) {
        // Cria e inicia 5 threads
        for (int i = 1; i <= 5; i++) {
            new Task("Thread " + i).start();
        }
    }
}