package lab4C;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MyFrame extends JFrame {
	
	private BoxModel	model;
	private BoxView		view;
        private Controller controller;
	
	private final int particleCount = 1000;
	
	public MyFrame(String stringIn) {
                super(stringIn);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initController();
			}
		});
	}
	
	private static void initController() {
		
		MyFrame frame = new MyFrame("Particle System");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.initGUI();
	}
	
	private void initGUI() {
		model = new BoxModel(particleCount);
		view = new BoxView(model);
        controller = new Controller(model, view);
                
		add(controller);
		pack();
		setVisible(true);
	}
}
