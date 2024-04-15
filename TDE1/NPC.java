public class NPC {
    private String name;
    private String[] dialogues;
    private int x, y; // Posição do NPC no mapa

    public NPC(String name, String[] dialogues, int x, int y) {
        this.name = name;
        this.dialogues = dialogues;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
