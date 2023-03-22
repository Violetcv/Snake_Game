//package snake_game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener,KeyListener {

    private int[] snake_xlength = new int[750];
    private int[] snake_ylength = new int[750];
    private int snake_length = 3;

    private int[] xpos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750};
    private int[] ypos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random();

    private int apple_xpos,apple_ypos;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int moves = 0;
    private int score = 0;
    private boolean game_over=false;

    private ImageIcon snake_logo = new ImageIcon(getClass().getResource("snake_logo.png"));
    private ImageIcon snake_head_left = new ImageIcon(getClass().getResource("snake_head_left.png"));
    private ImageIcon snake_head_right = new ImageIcon(getClass().getResource("snake_head_right.png"));
    private ImageIcon snake_head_up = new ImageIcon(getClass().getResource("snake_head_up.png"));
    private ImageIcon snake_head_down = new ImageIcon(getClass().getResource("snake_head_down.png"));
    private ImageIcon snake_body = new ImageIcon(getClass().getResource("snake_body.png"));
    private ImageIcon snake_apple = new ImageIcon(getClass().getResource("apple.png"));

    private Timer timer;
    private int delay = 100;//sets speed of snake

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        new_apple();
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        Color myPink = new Color(188, 27, 98);
        g.setColor(myPink);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);
        snake_logo.paintIcon(this, g, 320, 11);
        g.fillRect(24, 75, 851, 575);

        if (moves == 0){
            snake_xlength[0] = 100;
            snake_xlength[1] = 85;
            snake_xlength[2] = 70;

            snake_ylength[0] = 100;
            snake_ylength[1] = 108;
            snake_ylength[2] = 108;
        }
    
        if (left){
            snake_head_left.paintIcon(this, g, snake_xlength[0], snake_ylength[0]);
        }if (right){
            snake_head_right.paintIcon(this, g, snake_xlength[0], snake_ylength[0]);
        }if (up){
            snake_head_up.paintIcon(this, g, snake_xlength[0], snake_ylength[0]);
        }if (down){
            snake_head_down.paintIcon(this, g, snake_xlength[0], snake_ylength[0]);
        }
        for(int i = 1 ; i<snake_length ; i++){
            snake_body.paintIcon(this, g, snake_xlength[i], snake_ylength[i]);
        }
        snake_apple.paintIcon(this, g, apple_xpos, apple_ypos);

        if (game_over){
            g.setColor(myPink);
            g.setFont(new Font("Palentino Linotype",Font.BOLD,50));
            g.drawString("Game Over!", 300 , 300);

            g.setFont(new Font("Palentino Linotype",Font.PLAIN,20));
            g.drawString("Press SPACE to Restart.", 320 , 350);
        }

            g.setColor(myPink);
            g.setFont(new Font("Palentino Linotype", Font.PLAIN, 14));
        g.drawString("Score: "+score, 750, 30);
        g.drawString("Length: "+snake_length, 750 , 50);

        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {   
        for(int i = snake_length-1;i>0;i--){
            snake_xlength[i]=snake_xlength[i-1];//-1 moves the body left
            snake_ylength[i] = snake_ylength[i-1];//-1 moves the body up
        }
        if(left){
            snake_xlength[0]=snake_xlength[0]-25;
        }if(right){
            snake_xlength[0]=snake_xlength[0]+25;
        }if(up){
            snake_ylength[0]=snake_ylength[0]-25;
        }if(down){
            snake_ylength[0]=snake_ylength[0]+25;
        }

        if(snake_xlength[0]>850) snake_xlength[0]=25;
        if(snake_xlength[0]<25) snake_xlength[0]=850;
        
        
        if(snake_ylength[0]>650) snake_ylength[0]=75;
        if(snake_ylength[0]<75) snake_ylength[0]=650;

        eats();
        hit_body();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }

        if(e.getKeyCode()==KeyEvent.VK_LEFT&&(!right)){
            left= true;
            //right=false;
            up=false;
            down=false;
            moves++;
        }if(e.getKeyCode()==KeyEvent.VK_RIGHT&&(!left)){
            //left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }if(e.getKeyCode()==KeyEvent.VK_UP&&(!down)){
            left= false;
            right=false;
            up=true;
            //down=false;
            moves++;
        }if(e.getKeyCode()==KeyEvent.VK_DOWN&&(!up)){
            left=false;
            right=false;
            //up=false;
            down=true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void new_apple() {
        apple_xpos= xpos[random.nextInt(30)];
        apple_ypos= ypos[random.nextInt(23)];

        for (int i = snake_length-1;i>=0;i--){
            if (snake_xlength[0]==apple_xpos&&snake_ylength[0]==apple_ypos){
                new_apple();
            }
        }
    }

    private void eats() {
        if(snake_xlength[0]==apple_xpos && snake_ylength[0]==apple_ypos){
            new_apple();
            snake_length++;
            score++;
        }
    }

    
    private void hit_body() {
        for (int i = snake_length -1;i>=0;i-- ){
            if(snake_xlength[i]==snake_xlength[0]&&snake_ylength[i]==snake_ylength[0]){
                timer.stop();
                game_over=true;
            }
        }
    }
    
    private void restart(){
        game_over=false;
        moves=0;
        score=0;
        snake_length= 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }
}