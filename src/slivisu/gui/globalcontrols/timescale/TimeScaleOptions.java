package slivisu.gui.globalcontrols.timescale;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimeScaleOptions extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeScale timeScale = null;
	private JSpinner spinSegments, spinMinAge, spinMaxAge;

	public TimeScaleOptions(Frame parent, boolean modal, TimeScale timeScale) {
		super(parent, modal);
		this.timeScale = timeScale;
		initComponents();
		setSettings();
		this.setAlwaysOnTop(true);
	}

	private void setSettings() {
		spinSegments.setValue(timeScale.getTimeslider().getSegmentCount());
	}

	private void initComponents() {
		this.add(panIntervalOptions());
		this.setSize(300, 150);
		this.setVisible(true);
	}

	private JPanel panIntervalOptions() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 4));
		p.setBorder(new TitledBorder("interval settings"));
		JLabel l1 = new JLabel("segments: ");
		l1.setFont(new Font("SansSerif", Font.PLAIN, 10));
		p.add(l1);
		JLabel l2 = new JLabel("min age: ");
		l2.setFont(new Font("SansSerif", Font.PLAIN, 10));
		p.add(l2);
		JLabel l3 = new JLabel("max age: ");
		l3.setFont(new Font("SansSerif", Font.PLAIN, 10));
		p.add(l3);
		p.add(new JLabel());
		p.add(spinGroups());
		p.add(spinMaxAge());
		p.add(spinMinAge());
		p.add(bClose());
		return p;
	}

	private JButton bClose() {
		JButton bClose = new JButton("close");
		bClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeScale.getTimeslider().setMinimumValue(Double.parseDouble("" + spinMinAge.getValue()));
				timeScale.getTimeslider().setMaximumValue(Double.parseDouble("" + spinMaxAge.getValue()));
				closeMenu();
			}
		});
		return bClose;
	}

	private void closeMenu() {
		this.dispose();
	}

	private JSpinner spinGroups() {
		spinSegments = new JSpinner();
		spinSegments.setFont(new Font("SansSerif", Font.PLAIN, 10));
		spinSegments.setModel(new SpinnerNumberModel(Integer.valueOf(10), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinSegments.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setSegments(Integer.parseInt(spinSegments.getValue().toString()));
			}
		});

		return spinSegments;
	}

	private void setSegments(int segments) {
		double minV = timeScale.getTimeslider().getMinimumValue();
		double maxV = timeScale.getTimeslider().getMaximumValue();
		timeScale.getTimeslider().setSegmentSize((maxV - minV) / segments);
		timeScale.setSegments(segments);
	}

	private JSpinner spinMinAge() {
		spinMinAge = new JSpinner();
		spinMinAge.setFont(new Font("SansSerif", Font.PLAIN, 10));
		spinMinAge.setModel(new SpinnerNumberModel(Integer.valueOf(10), 0, null, Integer.valueOf(1)));
		spinMinAge.setValue(timeScale.getTimeslider().getMinimumValue());
		return spinMinAge;
	}

	private JSpinner spinMaxAge() {
		spinMaxAge = new JSpinner();
		spinMaxAge.setFont(new Font("SansSerif", Font.PLAIN, 10));
		spinMaxAge.setModel(new SpinnerNumberModel(Integer.valueOf(10), 0, null, Integer.valueOf(1)));
		spinMaxAge.setValue(timeScale.getTimeslider().getMaximumValue());
		return spinMaxAge;
	}
}
