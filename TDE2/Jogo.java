import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Jogo extends JFrame implements KeyListener {
    private JLabel statusBar;
    private Mapa mapa;
    private GameTimer timer;
    //private final Color fogColor = new Color(128, 0, 128, 100); // Cor roxa com transparência mais baixa para nevoa
    private final Color characterColor = Color.WHITE; // Cor branca para o personagem
    private NPCManager npcManager; // Gerenciador de NPCs

    public Jogo(String arquivoMapa) {
        timer = new GameTimer(this);
        Thread threadTimer = new Thread(timer);
        threadTimer.start();
    
        setTitle("TDE1 - Grupo 12");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    
        // Cria o mapa do jogo
        mapa = new Mapa(arquivoMapa);
    
        // Cria a instância do NPCManager, passando o jogo atual
        npcManager = new NPCManager(this);

        // Cria a thread para o NPCManager e a inicia
        Thread threadNPCManager = new Thread(npcManager);
        threadNPCManager.start();

        // Adiciona NPCs ao gerenciador
        npcManager.addNPC(new NPC(10, 20, "Good Game", new String[]{"Olá, pequeno gafanhoto", "Bem-vindo ao meu jogo! \n De presente, receba 10 segundos a mais de jogo ☆"}));
        npcManager.addNPC(new NPC(100, 100, "So bad", new String[]{"Não caia em armadilhas", "Para que você fique atento, acaba de perder 5 segundos de jogo ✘"}));

        RandomPaint pintorMapa = new RandomPaint(this, mapa, timer);
        Thread threadPintor = new Thread(pintorMapa);
        threadPintor.start();
    
        ThreadMoeda threadM = new ThreadMoeda(mapa);
        Thread threadMoeda = new Thread(threadM);
        threadMoeda.start();
    
        // Configuração do painel de mapa e botões
        JPanel mapPanel = setupMapPanel();
        JPanel buttonPanel = setupButtonPanel();
    
        // Barra de status
        statusBar = new JLabel();
        updateStatusBar();  // Atualiza a barra de status imediatamente após sua criação
    
        // Configuração do painel sul
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(buttonPanel);
        southPanel.add(statusBar);
    
        // Adição dos painéis ao JFrame
        add(mapPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    
        // Finalização da configuração do JFrame
        pack();
        addKeyListener(this);
    }

    private JPanel setupMapPanel() {
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                Font font = new Font("Roboto", Font.BOLD, 12);
                g.setFont(font);
                desenhaMapa(g);
                desenhaPersonagem(g);
            }
        };
        mapPanel.setPreferredSize(new Dimension(800, 600));
        return mapPanel;
    }
    
    private JPanel setupButtonPanel() {
        JButton btnUp = new JButton("Cima (W)");
        JButton btnDown = new JButton("Baixo (S)");
        JButton btnRight = new JButton("Direita (D)");
        JButton btnLeft = new JButton("Esquerda (A)");
    
        btnUp.setFocusable(false);
        btnDown.setFocusable(false);
        btnRight.setFocusable(false);
        btnLeft.setFocusable(false);
    
        btnUp.addActionListener(e -> move(Direcao.CIMA));
        btnDown.addActionListener(e -> move(Direcao.BAIXO));
        btnRight.addActionListener(e -> move(Direcao.DIREITA));
        btnLeft.addActionListener(e -> move(Direcao.ESQUERDA));
    
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(btnUp);
        buttonPanel.add(btnDown);
        buttonPanel.add(btnRight);
        buttonPanel.add(btnLeft);
        return buttonPanel;
    }

    public void stopGame() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void updateStatusBar() {
        if (statusBar != null) {
            statusBar.setText("Posição: (" + mapa.getX() + "," + mapa.getY() + ") - " +
                              "Número de moedas: " + numCoinsCollected + " - " +
                              "Tempo restante: " + timer.getTempoRestante() + " segundos");
        }
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void move(Direcao direcao) {
        if (mapa == null)
            return;
    
        if (!mapa.move(direcao))
            return;
    
        updateStatusBar(); // Atualiza a barra de status após o movimento
        repaint();
    }

    private int numCoinsCollected = 0; // Contagem de moedas coletadas

    public void interage() {
        if (mapa == null)
            return;
    
        boolean collected = mapa.interage();
        if (collected) {
            numCoinsCollected++;
            JOptionPane.showMessageDialog(this, "Você coletou uma moeda!");
        } else {
            JOptionPane.showMessageDialog(this, "Não há moedas próximas para coletar.");
        }
        updateStatusBar(); // Atualiza a barra de status após interagir
        repaint();
    }

    public void ataca() {
        if (mapa == null)
            return;

        String status = mapa.ataca();

        // Atualiza a barra de status
        if (statusBar != null)
            statusBar.setText(status);
    }

    private void desenhaMapa(Graphics g) {
        int tamanhoCelula = mapa.getTamanhoCelula();
        for (int i = 0; i < mapa.getNumLinhas(); i++) {
            for (int j = 0; j < mapa.getNumColunas(); j++) {
                int posX = j * tamanhoCelula;
                int posY = (i + 1) * tamanhoCelula;

                if (mapa.estaRevelado(j, i)) {
                    ElementoMapa elemento = mapa.getElemento(j, i);
                    if (elemento != null) {
                        //System.out.println("Elemento: " + elemento.getSimbolo() + " " + elemento.getCor());
                        g.setColor(elemento.getCor());
                        g.drawString(elemento.getSimbolo().toString(), posX, posY);
                    }
                } else {
                    // Pinta a área não revelada
                    //g.setColor(fogColor);
                    //g.fillRect(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                }
            }
        }
    }

    private void desenhaPersonagem(Graphics g) {
        g.setColor(characterColor);
        g.drawString("☺", mapa.getX(), mapa.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não necessário
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W: // Tecla 'W' para cima
                move(Direcao.CIMA);
                break;
            case KeyEvent.VK_S: // Tecla 'S' para baixo
                move(Direcao.BAIXO);
                break;
            case KeyEvent.VK_A: // Tecla 'A' para esquerda
                move(Direcao.ESQUERDA);
                break;
            case KeyEvent.VK_D: // Tecla 'D' para direita
                move(Direcao.DIREITA);
                break;
            case KeyEvent.VK_E: // Tecla 'E' para interagir
                interage();
                break;
            case KeyEvent.VK_J: // Tecla 'J' para ação secundária
                ataca();
                break;
        }
    }

    public void adicionarTempo(int segundos) {
        timer.adicionarTempo(segundos);
    }
    
    public void removerTempo(int segundos) {
        timer.removerTempo(segundos);
    }

    public synchronized void atualizarBarraDeStatus() {
        SwingUtilities.invokeLater(() -> {
            updateStatusBar();
        });
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não necessário
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Jogo("mapa.txt").setVisible(true);
        });
    }
}
