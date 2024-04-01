import java.util.Random;
import java.util.concurrent.Semaphore;

public class Banheiro {
    private Semaphore sem;

    public Banheiro() {
        sem = new Semaphore(3);
    }

    public void usar(int id) throws InterruptedException {
        sem.acquire();
        // if (sem.tryAcquire(3, TimeUnit.SECONDS) == false) {
        //     System.out.println(id + " desistiu de usar o banheiro");
        //     return;
        // }
        System.out.println(id + " usando banheiro");
        Random rand = new Random(); 
        int tempo = 1000 + rand.nextInt(4000);
        Thread.sleep(tempo);
        System.out.println(id + " saiu do banheiro");
        sem.release();
    }

    public static void main(String args[]) {
        Banheiro banheiro = new Banheiro();
        for (int i = 0; i < 10; i++) {
            // Usando classe Pessoa como Runnable
            new Thread(new Pessoa(i, banheiro)).start();
            // Pessoa p = new Pessoa(i, banheiro);
            // Thread t = new Thread(p);
            // t.start();

            // Usando classe anônima
            // new Thread(new Runnable() {
            //     // Get id of thread
            //     public void run() {
            //         int id = (int) Thread.currentThread().getId();
            //         System.out.println(id + " esperando pra entrar no banheiro");
            //         try {
            //             banheiro.usar(id);
            //         } catch (InterruptedException e) {
            //             e.printStackTrace();
            //         }
            //         System.out.println(id + " saiu do banheiro");
            //     }
            // }).start();
        }
    }
}

class Pessoa implements Runnable {
    private int id;
    private Banheiro banheiro;

    public Pessoa(int id, Banheiro banheiro) {
        this.id = id;
        this.banheiro = banheiro;
    }

    public void run() {
        System.out.println(id + " esperando pra entrar no banheiro");
        try {
            banheiro.usar(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
