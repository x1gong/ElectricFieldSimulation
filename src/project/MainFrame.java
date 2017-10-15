package project;

import java.awt.*;
import java.awt.event.*;


public class MainFrame extends Frame {
    
    private static final int X = 100;
    private static final int Y = 100;
    public static final int HEIGHT = 620;
    public static final int WIDTH = 800;
    
    private Test test;
    private Listener listener;
    
    
    public MainFrame(Test test) {
        this.test = test;
    }
    
    public void launchFrame() {
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        this.setTitle("Electric Field Simulation  --  By Xiangyi Gong");
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.listener = new Listener();
        this.addMouseListener(listener);
        this.setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        
        for (int i = 0; i < test.charges.size(); i ++) {
            Charge charge = test.charges.get(i);
            charge.draw(g);
        }
        
        this.drawLines(g);
        
//        g.setColor(Color.RED);
//        g.drawOval(380, 280, 40, 40);
//        g.drawOval(340, 240, 120, 120);
//        g.drawOval(260, 160, 280, 280);
//        g.drawOval(100, 0, 600, 600);
    }
    
    private void drawLines(Graphics g) {
        
        for (Charge source : test.charges) {
            for (Point point : source.getInitialPoints()) {                
                Line line = new Line(point);
                Point p = new Point(point.getX(), point.getY(), source, test);
                while (p.hasNextPoint()) {
                    Force fP = p.totalForce();
                    p = p.nextPoint(fP.getX(), fP.getY());
                    line.addPoint(p);
                }
                line.draw(g);
            }
        }
    }

    private class Listener extends MouseAdapter {
        
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            
            
            
            Charge c;
            
            if (e.getButton() == MouseEvent.BUTTON1) {
                c = new Charge(x, y, 1);
            } else {
                c = new Charge(x, y, -1);
            }
             
            test.charges.add(c);
            repaint();
        }
        
    }
}
