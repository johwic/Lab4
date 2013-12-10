package lab4E5;

/* written by Johan */

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller extends JPanel implements ActionListener, ChangeListener {

	private BoxModel model;
	private BoxView view;
	private Timer timer;
	private JSlider LSlider, deltaSlider;

	private static final int X = 0;
	private static final int Y = 1;

	private static final int L_MIN = 0;
	private static final int L_MAX = 100;
	private static final int L_INIT = 10;

	private static final int DELTA_MIN = 16;
	private static final int DELTA_MAX = 1000;
	private static final int DELTA_INIT = 33;

	private static final int 	RECORD_STEPS	= 100;
	private static final String	RECORD_FILENAME = "particle_data.txt";

	private int				dt 			= DELTA_INIT;
	private double 			time 		= 0;
	private int			stepCount		= 0;

	private PrintWriter 	writer	= null;
	private StringBuilder 	data	= null;


	public Controller(BoxModel modelIn, BoxView viewIn) throws FileNotFoundException  {
		model = modelIn;
		view = viewIn;

		LSlider = new JSlider(L_MIN, L_MAX, L_INIT);
		deltaSlider = new JSlider(DELTA_MIN, DELTA_MAX, DELTA_INIT);
		LSlider.addChangeListener(this);
		deltaSlider.addChangeListener(this);

		JPanel wrapper = new JPanel(new GridLayout(0,1));
		wrapper.add(LSlider);
		wrapper.add(deltaSlider);

		add(wrapper);
		add(view);

		model.setL((double) L_INIT);

		startWriter();
		System.out.println("Started writer.");

		timer = new Timer(DELTA_INIT, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	private void startWriter() throws FileNotFoundException {

		writer = new PrintWriter(RECORD_FILENAME);
	}

	/** Assumes writer already created and file ready to write. */
	private void writeLine() {
		int len = model.getParticleCount();

		/* prealloc len*2*8 + len*2 + 8 + 2 to fit all doubles, commas and whitespace
		 * ? does len become the # chars? how many chars for a double ?
		 */
		data = new StringBuilder(10 + len*18);

		/* build data string */
		data.append(time);
		double[][] pos = model.getParticlePos2D();
		for (int i = 0; i < len; i++) {
			data.append(", ");
			data.append(pos[i][X]);
			data.append(", ");
			data.append(pos[i][Y]);
		}

		/* write a line */
		writer.println(data);
	}

	private void stopWriter() {
		if (writer != null) {
			writer.close();
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
			model.setL((double) source.getValue());
		}

		if (source == deltaSlider) {
			dt = source.getValue();
			updateTimer(source.getValue());
			// TODO find out why animation pauses when I move deltaSlider.
			// UpdateTimer() restarts timer for every new value.
			// Fix this (if we have time).
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (stepCount > 0) {
			time += dt;
		}

		model.updatePosition();

		if (stepCount < RECORD_STEPS) {
			writeLine();
		}
		if (stepCount == RECORD_STEPS) {
			stopWriter();
			System.out.println("Stopping writer at " + stepCount + " steps.");
		}

		stepCount++;

		view.repaint();
	}
}
