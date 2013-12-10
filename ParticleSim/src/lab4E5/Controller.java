package lab4E5;

/* written by Johan */

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;

public class Controller extends JPanel implements ActionListener, ChangeListener {

	private BoxModel model;
	private BoxView view;
	private Timer timer;
	private JSlider LSlider, deltaSlider;

	private static final int L_MIN = 0;
	private static final int L_MAX = 100;
	private static final int L_INIT = 10;

	private static final int DELTA_MIN = 16;
	private static final int DELTA_MAX = 1000;
	private static final int DELTA_INIT = 33;
	
	private static final int RECORD_STEPS	= 100;
	private static final String	RECORD_FILENAME = "particle_data.txt";
	
	private FileWriter 		writer	= null;
	private StringBuilder 	data	= null;
	

	public Controller(BoxModel model, BoxView view)  {
		this.model = model;
		this.view = view;

		LSlider = new JSlider(L_MIN, L_MAX, L_INIT);
		deltaSlider = new JSlider(DELTA_MIN, DELTA_MAX, DELTA_INIT);
		LSlider.addChangeListener(this);
		deltaSlider.addChangeListener(this);

		JPanel wrapper = new JPanel(new GridLayout(0,1));
		wrapper.add(LSlider);
		wrapper.add(deltaSlider);

		add(wrapper);
		add(view);

		this.model.setL((double) L_INIT);

		timer = new Timer(DELTA_INIT, this);
		timer.setInitialDelay(1000);
		timer.start();
	}
	
	private void startWriter() throws IOException {
		try{
			writer = new FileWriter(RECORD_FILENAME);
			int startLen = model.getParticleCount();
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	protected void updateTimer(int delta) { // why protected?
		timer.stop();
		timer = new Timer(delta, this);
		timer.start();
	}

	public void stateChanged(ChangeEvent e) {	
		JSlider source = (JSlider)e.getSource();

		if (source == LSlider) {
			this.model.setL((double) source.getValue());
		}

		if (source == deltaSlider) {
			updateTimer(source.getValue());	
			// TODO find out why animation pauses when I move deltaSlider.
			// UpdateTimer() restarts timer for every new value.
			// Fix this (if we have time).
		}
	}

	public void actionPerformed(ActionEvent e) {
		this.model.updatePosition();
		this.view.repaint();
	}
}
