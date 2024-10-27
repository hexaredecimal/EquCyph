package equcyph;

import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author hexaredecimal
 */
public class Launcher {
	
	public static void main(String[] args) {
		var splash = new Splash(); 
		splash.setup();
		splash.setVisible(true);
		
		Timer timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// splash.dispatchEvent(new WindowEvent(splash, WindowEvent.WINDOW_CLOSING));
				splash.dispose();
				new EquCyph();
			}
		});

		timer.setRepeats(false);
		timer.start(); 
	}
}
