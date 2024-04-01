public class MinhaPrimeiraThread extends Thread {
    private int valor = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            valor++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        MinhaPrimeiraThread t1 = new MinhaPrimeiraThread();
        MinhaSegundaThread t2 = new MinhaSegundaThread();
        // inicia
        t1.start();
        t2.start();

        // espera
        t1.join();
        t2.join();
    }
}

class MinhaSegundaThread extends Thread {
    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}