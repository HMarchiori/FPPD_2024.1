package CópiaT1;
public class NPC {
    private int x;
    private int y;
    private String name;
    private String[] dialogues;
    private boolean hasInteracted; // Variável para rastrear se o NPC já interagiu

    public NPC(int x, int y, String name, String[] dialogues) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.dialogues = dialogues;
        this.hasInteracted = false; // Inicializa como não tendo interagido
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public boolean hasInteracted() {
        return hasInteracted;
    }

    public void setHasInteracted(boolean hasInteracted) {
        this.hasInteracted = hasInteracted;
    }
}
