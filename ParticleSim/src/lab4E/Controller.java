package lab4E;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller extends JPanel implements ChangeListener {

	private	static final int	ELL_INIT				= 10;
	private	static final int	ELL_MAX					= 1000;
	private	static final int	ELL_MIN					= 1;
	private static final int	ELL_MAJ_TICK_SPACING	= 250;
	private static final int	ELL_MIN_TICK_SPACING	= 50;
	private static final double ELL_FACTOR				= 1.0/((double)ELL_MAX-ELL_MIN);
	
	private static final int	FPS_INIT				= 30;
	private static final int	FPS_MAX					= 60;
	private static final int	FPS_MIN					= 1;
	private static final int	FPS_MAJ_TICK_SPACING	= 10;
	private static final int	FPS_MIN_TICK_SPACING	= 1;
	
	private double dt	= 1000.0/(double)FPS_INIT;
	private double L	= (double)ELL_INIT * ELL_FACTOR;
	
	private JSlider		ellSlider	= new JSlider(JSlider.HORIZONTAL, ELL_MIN, ELL_MAX, ELL_INIT);
	private JSlider		fpsSlider	= new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
	
	private	GridLayout	gridLayout	= new GridLayout(2,1);
	
	private BoxModel	model;
	private BoxView		view;
	
	public Controller(BoxModel modelIn, BoxView viewIn) {
		
		model = modelIn;
		view = viewIn;
		
		setLayout(gridLayout);
		// TODO padding
		// TODO labels on slider
		initSliders();
	}
	
	public void initSliders() {
		
		ellSlider.setMajorTickSpacing(ELL_MAJ_TICK_SPACING);
		ellSlider.setMinorTickSpacing(ELL_MIN_TICK_SPACING);
		ellSlider.setPaintTicks(true);
		
		fpsSlider.setMajorTickSpacing(FPS_MAJ_TICK_SPACING);
		fpsSlider.setMinorTickSpacing(FPS_MIN_TICK_SPACING);
		fpsSlider.setPaintTicks(true);
		
		ellSlider.addChangeListener(this);
		fpsSlider.addChangeListener(this);
	}
	
	public void stateChanged(ChangeEvent e) {
		// TODO implement
		if (e.getSource() == ellSlider) {
			
		}
		if (e.getSource() == fpsSlider) {
			
		}
		
	}
	
	private double fpsToSeconds(int fpsIn) {
		return 1000.0/(double)fpsIn;
	}
	
	private double normalizeEll(int ellIn) {
		return (double)ellIn*ELL_FACTOR;
	}
}
