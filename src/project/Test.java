
package project;

import java.util.ArrayList;

public class Test {
    
    public ArrayList<Charge> charges;
    
    public Test() {
        charges = new ArrayList<>();
        MainFrame frame = new MainFrame(this);
        frame.launchFrame();
    }
    
    public static void main(String args[]) {
        new Test();
    }
}
