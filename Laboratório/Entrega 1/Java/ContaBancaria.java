public class ContaBancaria {
    private double saldo;
    private Object lock = new Object();

    public ContaBancaria(int saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public double sacar(double valor) {
        synchronized(lock) {
           this.saldo -= valor;
        }
        return saldo;
    }

    public synchronized double depositar(double valor) {
        synchronized(lock) {
            this.saldo += valor;
        }
        return saldo;
    }

    public static void main(String args[]) throws InterruptedException {
        ContaBancaria conta = new ContaBancaria(1000);
        Thread t1 = new Thread(new Depositador(conta));
        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    conta.sacar(200);
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Saldo final: " + conta.getSaldo());
    }
}

class Depositador implements Runnable {
    private ContaBancaria conta;

    public Depositador(ContaBancaria conta) {
        this.conta = conta;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10000; i++) {
            conta.depositar(200);
        }
    }    
}
