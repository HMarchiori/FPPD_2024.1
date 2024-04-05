public class Inimigo extends Thread {
    public void run() {
        System.out.println("Inimigo: Iniciando execução");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
