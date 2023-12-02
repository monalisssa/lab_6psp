package src.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AirplaneRicing extends JFrame {
    private int x1, x2, x3; // координата x объекта
    private int y1, y2, y3; // координата y объекта
    private BufferedImage plane1_img, plane2_img, plane3_img;
    private final Plane1 plane1;
    private final Plane2 plane2;
    private final Plane3 plane3;
    private boolean isMoving;
    private int dx1, dx2,dx3; // смещение по оси x


    public AirplaneRicing() {

        x1 = 60; y1 = 170;
        x2 = 60; y2 = 50;
        x3 = 60; y3 = 70;
        isMoving = false;

        setTitle("Airplane racing");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel("D:\\background.png");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());

        // Панель для всех самолетов
        JPanel planesPanel = new JPanel();
        planesPanel.setOpaque(false);
        planesPanel.setLayout(new BoxLayout(planesPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(planesPanel, BorderLayout.CENTER);

        plane1 = new Plane1(); planesPanel.add(plane1);
        plane2 = new Plane2(); planesPanel.add(plane2);
        plane3 = new Plane3(); planesPanel.add(plane3);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        buttonsPanel.add(startButton);
        buttonsPanel.add(stopButton);
        planesPanel.add(buttonsPanel);

        startButton.addActionListener(arg->
                {
                    isMoving = true;

                    Random random = new Random();
                    dx1 = random.nextInt(1, 20);
                    dx2 = random.nextInt(1, 20);
                    dx3 = random.nextInt(1, 20);

                    plane1.startMove();
                    plane2.startMove();
                    plane3.startMove();
                }
        );

        stopButton.addActionListener(arg->
                {
                    isMoving = false;

                    plane1.fall();
                    plane2.fall();
                    plane3.fall();

                    showDialogWindow(0);

                }
        );

    }


    private class Plane1 extends JPanel {
        public Plane1() {
            setOpaque(false);
            setPreferredSize(new Dimension(100,185));
            try {
                plane1_img = ImageIO.read(new File("D:\\plane_1.png"));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(plane1_img, x1, y1, 100, 50, this);
        }

        public void startMove()
        {
            new Thread(() -> {
                while (isMoving) {
                    x1 += dx1;
                    repaint();

                    if(x1 >= 1000 && x2<=1000 && x3<=1000)
                    {
                        isMoving = false;
                        showDialogWindow(1);

                    }

                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }

        public void fall()
        {
            new Thread(() -> {
                while (y1<=this.getHeight()) {
                    y1 += dx1;
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }

    }

    private class Plane2 extends JPanel {
        public Plane2() {
            setOpaque(false);
            setPreferredSize(new Dimension(100, 100));
                try {
                    plane2_img = ImageIO.read(new File("D:\\plane_2.png"));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(plane2_img, x2, y2, 150, 130, this);
        }


        public void startMove()
        {
            new Thread(() -> {
                while (isMoving) {
                    x2 += dx2;

                    if(x2 >= 1000 && x1<=1000 && x3<=1000)
                    {
                        isMoving = false;
                        showDialogWindow(2);

                    }
                    repaint();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }

        public void fall()
        {
            new Thread(() -> {
                while (y2<=this.getHeight()) {
                    y2 += dx2;
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }

    }

    private class Plane3 extends JPanel {
        public Plane3() {
            setOpaque(false);
            setPreferredSize(new Dimension(100, 180));
            try {
                plane3_img = ImageIO.read(new File("D:\\plane_2.png"));
            } catch (IOException exc) {
                exc.printStackTrace();
            }

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(plane3_img, x3, y3, 150, 130, this);
        }

        public void startMove()
        {
            new Thread(() -> {
                while (isMoving) {
                    x3 += dx3;

                    if(x3 >= 1000 && x1<=1000 && x2<=1000)
                    {
                        isMoving = false;
                        showDialogWindow(3);
                    }
                    repaint();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }

        public void fall()
        {
            new Thread(() -> {
                while (y3<=this.getHeight()) {
                    y3 += dx3;
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }).start();

        }
    }

    private class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void showDialogWindow(int winner) {
        JDialog dialog = new JDialog(this, true);

        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel();

        if(winner != 0)
        {
            dialog.setTitle("Победа");
            label.setText("!ПОБЕДИТЕЛЬ! - " + winner + " самолёт");
        }
        else{
            dialog.setTitle("Пройденные дистанции");
            label.setText("<html>1-й самолёт - " + x1 + " м<br>2-й самолёт - " + x2 + " м<br>3-й самолёт - " + x3+ " м</html>");
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupPanel.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                x1 = 60; y1 = 170;
                x2 = 60;y2 = 50;
                x3 = 60; y3 = 70;
                repaint();
        }});

        popupPanel.add(closeButton, BorderLayout.SOUTH);

        dialog.add(popupPanel);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new AirplaneRicing().setVisible(true);

    }
}
