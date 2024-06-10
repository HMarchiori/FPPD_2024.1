

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Mapa {
    private List<String> mapa;
    private List<Moeda> moedas;
    private Map<Character, ElementoMapa> elementos;
    private Map<Point, ElementoMapa> elementosPorPosicao;
    private int x; // Posição inicial X do personagem
    private int y; // Posição inicial Y do personagem
    private final int TAMANHO_CELULA = 10; // Tamanho de cada célula do mapa
    private boolean[][] areaRevelada; // Rastreia quais partes do mapa foram reveladas
    private final Color brickColor = new Color(153, 76, 0); // Cor marrom para tijolos
    private final Color vegetationColor = new Color(34, 139, 34); // Cor verde para vegetação
    private final int RAIO_VISAO = 100; // Raio de visão do personagem

    public Mapa(String arquivoMapa) {
        mapa = new ArrayList<>();
        elementos = new HashMap<>();
        elementosPorPosicao = new HashMap<>();
        registraElementos();
        carregaMapa(arquivoMapa);
        areaRevelada = new boolean[mapa.size()+1000][mapa.get(0).length()+1000];
        char[][] maze = MazeGenerator.generateMaze();
        atualizaMapa(maze);
        atualizaCelulasReveladas();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamanhoCelula() {
        return TAMANHO_CELULA;
    }

    public int getNumLinhas() {
        return mapa.size();
    }

    public int getNumColunas() {
        return mapa.get(0).length();
    }

    public ElementoMapa getElemento(int x, int y) {
        Point p = new Point(x, y);
        ElementoMapa elementoEspecifico = elementosPorPosicao.get(p);
        if (elementoEspecifico != null) {
            // Retorna o elemento específico (como moedas) se existir nesta posição
            return elementoEspecifico;
        }
        // Se não houver um elemento específico, retorna o elemento padrão baseado no caractere do mapa
        Character id = mapa.get(y).charAt(x);
        return elementos.get(id);
    }
    

    public List<String> getMapa() {
        return mapa;
    }

    public List<Moeda> getMoedas() {
        return moedas;
    }

    public synchronized void colocarMoeda(int x, int y) {
        elementosPorPosicao.put(new Point(x, y), new Moeda('◉', Color.YELLOW, x, y));
        moedas.add(new Moeda('◉', Color.YELLOW, x, y));
    }

    public boolean estaRevelado(int x, int y) {
        return areaRevelada[y][x];
    }

    // Move conforme enum Direcao
    public boolean move(Direcao direcao) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case CIMA:
                dy = -TAMANHO_CELULA;
                break;
            case BAIXO:
                dy = TAMANHO_CELULA;
                break;
            case ESQUERDA:
                dx = -TAMANHO_CELULA;
                break;
            case DIREITA:
                dx = TAMANHO_CELULA;
                break;
            default:
                return false;
        }

        if (!podeMover(x + dx, y + dy)) {
            //System.out.println("Não pode mover");
            return false;
        }

        x += dx;
        y += dy;

        // Atualiza as células reveladas
        atualizaCelulasReveladas();
        return true;
    }

    // Verifica se o personagem pode se mover para a próxima posição
    private boolean podeMover(int nextX, int nextY) {
        int mapX = nextX / TAMANHO_CELULA;
        int mapY = nextY / TAMANHO_CELULA - 1;

        if (mapa == null)
            return false;

        if (mapX >= 0 && mapX < mapa.get(0).length() && mapY >= 1 && mapY <= mapa.size()) {
            char id;

            try {
               id = mapa.get(mapY).charAt(mapX);
            } catch (StringIndexOutOfBoundsException e) {
                return false;
            }

            if (id == ' ')
                return true;

            ElementoMapa elemento = elementos.get(id);
            if (elemento != null) {
                //System.out.println("Elemento: " + elemento.getSimbolo() + " " + elemento.getCor());
                return elemento.podeSerAtravessado();
            }
        }

        return false;
    }

    public boolean interage() {
        // Expande o alcance de verificação para duas células em todas as direções
        int[] dx = {-2, -1, 0, 1, 2};  // Deslocamentos em X
        int[] dy = {-2, -1, 0, 1, 2};  // Deslocamentos em Y
    
        for (int dxOffset : dx) {
            for (int dyOffset : dy) {
                int checkX = x / TAMANHO_CELULA + dxOffset;  // Calcula a posição X para verificar
                int checkY = y / TAMANHO_CELULA + dyOffset;  // Calcula a posição Y para verificar
                if (Math.abs(dxOffset) + Math.abs(dyOffset) <= 2) {  // Limita a verificação dentro de um raio de duas células
                    Point p = new Point(checkX, checkY);
                    ElementoMapa elemento = elementosPorPosicao.get(p);
                    if (elemento != null && elemento instanceof Moeda) {
                        elementosPorPosicao.remove(p);  // Remove a moeda do mapa
                        return true;  // Indica que uma moeda foi coletada
                    }
                }
            }
        }
        return false;  // Retorna falso se nenhuma moeda estiver próxima
    }

    public String ataca() {
        return "Ataca";
    }

    private void carregaMapa(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                mapa.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Set a random valid starting position
        Random random = new Random();
        boolean posValida = false;
        while (!posValida) {
            int randX = random.nextInt(getNumColunas());
            int randY = random.nextInt(getNumLinhas());
    
            if (mapa.get(randY).charAt(randX) == ' ') {  // Checks if the position is not a wall
                x = randX * TAMANHO_CELULA;  // Adjust position based on cell size
                y = randY * TAMANHO_CELULA;
                posValida = true;
            }
        }
    }

    // Atualiza o mapa com o resultado do gerador de labirinto
    private void atualizaMapa(char[][] maze) {
        mapa.clear();
        for (int i = 0; i < maze.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < maze[i].length; j++) {
                sb.append(maze[i][j]);
            }
            mapa.add(sb.toString());
        }
    }

    // Método para atualizar as células reveladas
    private void atualizaCelulasReveladas() {
        if (mapa == null)
            return;
        for (int i = Math.max(0, y / TAMANHO_CELULA - RAIO_VISAO); i < Math.min(mapa.size(), y / TAMANHO_CELULA + RAIO_VISAO + 1); i++) {
            for (int j = Math.max(0, x / TAMANHO_CELULA - RAIO_VISAO); j < Math.min(mapa.get(i).length(), x / TAMANHO_CELULA + RAIO_VISAO + 1); j++) {
                areaRevelada[i][j] = true;
            }
        }
    }

    // Registra os elementos do mapa
    private void registraElementos() {
        // Parede
        elementos.put('#', new Parede('▣', brickColor));
        // Vegetação
        elementos.put('V', new Vegetacao('♣', vegetationColor));
    }

    public boolean podeColocarMoeda(int x, int y) {
        char ch = mapa.get(x).charAt(y);
        return ch == ' '; // Retorna verdadeiro se o local é um espaço vazio
    }
}
