//package snake_game;
import java.awt.Color;
import javax.swing.JFrame;
public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10, 10, 905, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        Color myPeach = new Color(255, 151, 183);
        panel.setBackground(myPeach);
        frame.add(panel);

        frame.setVisible(true);
    }
}