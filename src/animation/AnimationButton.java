package animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AnimationButton extends JButton implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1547397154132363359L;
	
	private Animator animator;

	public AnimationButton(Animator animator){
		this.animator = animator;
		setText("Play");
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {


		if ((getText()).equals("Pause")){

			//------------------------------------------------------
			//------------------------------------------------------
			// Simulation unterbrechen
			animator.pause();
			//------------------------------------------------------
			//------------------------------------------------------

			setText("Play");
		}
		else{

			//------------------------------------------------------
			//------------------------------------------------------
			// Simulation fortsetzen
			animator.go();
			//------------------------------------------------------
			//------------------------------------------------------

			setText("Pause");
		}

	}
}
