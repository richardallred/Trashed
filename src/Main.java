
import javax.swing.JFrame;


public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 4648172894076113183L;

    Board board;
    
    public Main() {
	
	Board board= new Board();
	add(board);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,650);
        setLocationRelativeTo(null);
        setTitle("Trashed");
        setResizable(false);
        setVisible(true);
        
        
        
    }

    public static void main(String[] args) {
        new Main();
    }
    
}