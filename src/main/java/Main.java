import com.formdev.flatlaf.FlatDarkLaf;
import controller.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        } catch (Exception e) {
            System.err.println("Falló al inicializar LaF.");
        }
    }
}
