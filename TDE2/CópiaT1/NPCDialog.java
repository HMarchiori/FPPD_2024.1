package CópiaT1;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.*;

public class NPCDialog {
    private JDialog dialog;
    private JTextPane dialogueTextPane;

    public NPCDialog(JFrame parentFrame, String npcName, String[] dialogues) {
        // Cria o JDialog com o frame pai para garantir que ele seja modal
        dialog = new JDialog(parentFrame, npcName, true);
        
        // Configurações da janela
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false); // Impede que o usuário redimensione a janela
        
        // Define o ícone do NPC
        ImageIcon originalIcon = new ImageIcon("tip.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon npcIcon = new ImageIcon(resizedImage);
        
        JLabel npcLabel = new JLabel(npcIcon);
        npcLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza o ícone
        
        // Painel para conter o ícone
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        iconPanel.setPreferredSize(new Dimension(50, 300));
        iconPanel.add(npcLabel, BorderLayout.CENTER);
        
        // Painel de diálogo
        JPanel dialoguePanel = new JPanel();
        dialoguePanel.setBackground(Color.LIGHT_GRAY);
        dialoguePanel.setLayout(new BorderLayout());
        
        // Área de texto para o diálogo
        dialogueTextPane = new JTextPane();
        dialogueTextPane.setEditable(false);
        dialogueTextPane.setBackground(Color.WHITE);
        dialogueTextPane.setForeground(Color.BLACK);
        
        // Adiciona as falas do NPC à área de texto
        StyledDocument doc = dialogueTextPane.getStyledDocument();
        SimpleAttributeSet centerAttr = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttr, StyleConstants.ALIGN_CENTER);
        
        try {
            doc.insertString(doc.getLength(), String.join("\n", dialogues), null);
            doc.setParagraphAttributes(0, doc.getLength(), centerAttr, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Adiciona o JTextPane ao JScrollPane para barra de rolagem
        JScrollPane scrollPane = new JScrollPane(dialogueTextPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Adiciona o JScrollPane ao painel de diálogo
        dialoguePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Botão de ação
        JButton continueButton = new JButton("Continuar");
        continueButton.addActionListener(e -> dialog.dispose());
        continueButton.setBackground(Color.BLUE);
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        
        // Painel inferior para o botão
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(continueButton);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        // Adiciona os componentes ao diálogo
        dialog.add(iconPanel, BorderLayout.WEST);
        dialog.add(dialoguePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(parentFrame);
    }

    public void showDialog() {
        // Exibe o diálogo
        dialog.setVisible(true);
    }
}
