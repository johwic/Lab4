package lab4C;

/* written by Johan */

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class Controller extends JPanel implements ActionListener, ChangeListener {
    
    private BoxModel model;
    private BoxView view;
    private Timer timer;
    private JSlider LSlider, deltaSlider;
    
    private static final int L_MIN = 0;
    private static final int L_MAX = 100;
    private static final int L_INIT = 10;
    
    private static final int DELTA_MIN = 1;
    private static final int DELTA_MAX = 1000;
    private static final int DELTA_INIT = 100;
    
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
    
    protected void updateTimer(int delta) {
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
		}
    }
    
    public void actionPerformed(ActionEvent e) {
        this.model.updatePosition();
        this.view.repaint();
    }
}
