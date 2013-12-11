package lab4E5;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MyFrame extends JFrame {
	
	private BoxModel	model;
	private BoxView		view;
    private Controller 	controller;
	
	private final int particleCount = 1000;
	
	public MyFrame(String stringIn) {
                super(stringIn);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					initController();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void initController() throws FileNotFoundException {
		
		MyFrame frame = new MyFrame("Particle System");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.initGUI();
	}
	
	private void initGUI() throws FileNotFoundException {
		model = new BoxModel(particleCount);
		view = new BoxView(model);
        controller = new Controller(model, view);
                
		add(controller);
		pack();
		setVisible(true);
	}
}
