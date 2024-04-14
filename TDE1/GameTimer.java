public class GameTimer implements Runnable {
    private int tempo = 60;

    public GameTimer() {
    }
    
    public int getTempoRestante() {
        return tempo;
    }

    @Override
    public void run() {
        while (tempo > 0) {
            System.out.println(tempo);
            tempo--;
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}