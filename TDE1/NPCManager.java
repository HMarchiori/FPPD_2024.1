import java.util.ArrayList;
import java.util.List;


public class NPCManager implements Runnable {
    private Jogo jogo;
    private List<NPC> npcs;
    private final Object lock = new Object(); // Objeto de bloqueio

    public NPCManager(Jogo jogo) {
        this.jogo = jogo;
        this.npcs = new ArrayList<>();
    }

    // Adiciona um NPC ao jogo de forma sincronizada
    public void addNPC(NPC npc) {
        if (npc.hasInteracted() == false){
            synchronized (lock) {
                npcs.add(npc);
            }
        }
    }

    // Verifica a interação com os NPCs de forma sincronizada
    public void checkPlayerInteraction() {
        synchronized (lock) {
            int playerX = jogo.getMapa().getX();
            int playerY = jogo.getMapa().getY();

            for (NPC npc : npcs) {
                if (npc.getX() == playerX && npc.getY() == playerY) {
                    if (!npc.hasInteracted()){
                        // Inicia o diálogo com o NPC
                        startDialogue(npc);
                    }
                }
            }
        }
    }

    // Inicia o diálogo com o NPC
    private void startDialogue(NPC npc) {
        // Cria um novo NPCDialog com o frame pai (jogo) e as falas do NPC
        NPCDialog dialog = new NPCDialog(jogo, npc.getName(), npc.getDialogues());

        // Mostra o diálogo
        dialog.showDialog();

        // Define hasInteracted como true após a interação
        npc.setHasInteracted(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                checkPlayerInteraction();
                Thread.sleep(500); // Verifica a cada meio segundo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
