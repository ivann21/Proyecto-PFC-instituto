
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

//http://www.jtattoo.net

public class JTattooDemo extends JFrame {

    JButton jbBoton;

    public JTattooDemo(String look) {
        super("JTattoo Demo - " + look);        
        setSize(600, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        crearGUI();
        
        setVisible(true);
    }
    
    private void crearGUI() {
        jbBoton = new JButton("Botón");
        jbBoton.setBounds(200, 120, 200, 30);
        jbBoton.setToolTipText("Este botón no sirve!!!");
        add(jbBoton);
    }

    public static void main(String[] args) {
        String look = "";

        try {
            //look = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
            //look = "com.jtattoo.plaf.aero.AeroLookAndFeel";
            look = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
            //look = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
            //look = "com.jtattoo.plaf.fast.FastLookAndFeel";
            //look = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
            //look = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
            //look = "com.jtattoo.plaf.luna.LunaLookAndFeel";
            //look = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
            //look = "com.jtattoo.plaf.mint.MintLookAndFeel";
            //look = "com.jtattoo.plaf.noire.NoireLookAndFeel";
            //look = "com.jtattoo.plaf.smart.SmartLookAndFeel";
            //look = "com.jtattoo.plaf.texture.TextureLookAndFeel";
            UIManager.setLookAndFeel(look);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de cargar el paquete '" + look + "'.\n\n"
                    + "Se tomara el LookAndFeel por defecto.", "Error de paquete", JOptionPane.WARNING_MESSAGE);

            JFrame.setDefaultLookAndFeelDecorated(false);
            look = "";
        }

        JTattooDemo jt = new JTattooDemo(look);
    }
}
