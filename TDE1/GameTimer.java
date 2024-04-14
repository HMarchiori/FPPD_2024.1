import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameTimer implements Runnable {
    private int tempo = 60;  // tempo inicial em segundos
    private Jogo jogo;  // mantenha uma referência ao jogo

    public GameTimer(Jogo jogo) {
        this.jogo = jogo;  // inicialize a referência ao jogo
    }
    
    public int getTempoRestante() {
        return tempo;
    }

    @Override
    public void run() {
        while (tempo > 0) {
            System.out.println("Tempo restante: " + tempo + " segundos");
            tempo--;
            try {
                Thread.sleep(1000);  // espera por 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (jogo != null) {  // verifique se o jogo não é null antes de chamar stopGame
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(jogo, "Tempo esgotado!");
                jogo.stopGame();  // chama o método para parar o jogo
            });
        }
    }
}