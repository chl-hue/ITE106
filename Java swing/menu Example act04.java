import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class MenuExample {
    static JFrame frame;
	public static void main(String[] args) {
        frame = new JFrame("Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JMenu fileMenu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem openItem = new JMenuItem("Open");
        JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(new MenuActionListener());
        fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(closeItem);
		
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem cutItem = new JMenuItem("Cut");

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(cutItem);

        JMenuBar menubar = new JMenuBar();
        menubar.add(fileMenu);
        menubar.add(editMenu);

        frame.setJMenuBar(menubar);
        frame.setVisible(true);
    }
}
class MenuActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
            MenuExample.frame,
            "Got an ActionEvent at " + new Date(e.getWhen()) +
            " from " + e.getSource().getClass()
        );
    }
}
