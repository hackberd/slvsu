package slivisu.defaults.expandable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class ExpandablePanel extends JPanel implements ActionListener,
		ComponentListener {

	private static final long serialVersionUID = 1L;

	private static final String EXPANDDESCRIPTION = "Show Parameter";
	private static final String FOLDDESCRIPTION = "Hide Parameter";

	private ImageIcon expandIcon = null;
	private ImageIcon foldIcon = null;

	private static int MINHEIGHT = 20;

	private LinkedList<ExpandListener> listeners = new LinkedList<ExpandListener>();

	private Color expandButtonColor = null;
	
	// allow to use different titles for different states
	private boolean changeHeading = false;
	private String collComp;
	private String exComp;
	// --------------------------------------------------

	private Component description = null;
	private JButton expandButton = null;
	private Component expandPanel = null;

	private boolean expanded = false;

	// listener functions
	public void addExpandListener(ExpandListener listener) {
		listeners.add(listener);
	}

	public void removeExpandListener(ExpandListener listener) {
		listeners.remove(listener);
	}

	private void signalExpanded() {
		for (ExpandListener listener : listeners) {
			listener.componentExpanded(this);
		}
	}

	private void signalFolded() {
		for (ExpandListener listener : listeners) {
			listener.componentFolded(this);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param panelName
	 *            Name of the expandable panel, will be used as content for a
	 *            label.
	 * @param panel
	 *            The panel to expand and fold dynamically.
	 * @param expanded
	 *            The status of the panel, true if the panel shall be visible
	 *            from beginning.
	 */
	public ExpandablePanel(String panelName, Component panel, boolean expanded) {
		this(null, panelName, null, panel, expanded, false);
	}

	/**
	 * Constructor
	 * 
	 * @param panelName
	 *            Name of the expandable panel, will be used as content for a
	 *            label.
	 * @param panel
	 *            The panel to expand and fold dynamically.
	 * @param expanded
	 *            The status of the panel, true if the panel shall be visible
	 *            from beginning.
	 * @param indented
	 *            True if the component should be indented.
	 */
	public ExpandablePanel(String panelName, Component panel, boolean expanded,	boolean indented) {
		this(null, panelName, null, panel, expanded, indented);
	}

	/**
	 * Constructor
	 * 
	 * @param visibleComponent
	 *            A component which shall be used as the header of the
	 *            expandable panel.
	 * @param panel
	 *            The panel to expand and fold dynamically.
	 * @param expanded
	 *            The status of the panel, true if the panel shall be visible
	 *            from beginning.
	 */
	public ExpandablePanel(String collapsed, String exp, Component panel, boolean expanded) {
		this(null, collapsed, exp, panel, expanded, false);
	}
	
	/**
	 * Constructor
	 * 
	 * @param visibleComponent
	 *            A component which shall be used as the header of the
	 *            expandable panel.
	 * @param panel
	 *            The panel to expand and fold dynamically.
	 * @param expanded
	 *            The status of the panel, true if the panel shall be visible
	 *            from beginning.
	 */
	public ExpandablePanel(Component visibleComponent, Component panel, boolean expanded) {
		this(visibleComponent, null, null, panel, expanded, false);
	}

	/**
	 * Constructor
	 * 
	 * @param visibleComponent
	 *            A component which shall be used as the header of the
	 *            expandable panel.
	 * @param panel
	 *            The panel to expand and fold dynamically.
	 * @param expanded
	 *            The status of the panel, true if the panel shall be visible
	 *            from beginning.
	 * @param indented
	 *            True if the component should be indented.
	 */
	public ExpandablePanel(Component visibleComponent, Component panel, boolean expanded, boolean indented) {
		this(visibleComponent, null, null, panel, expanded, indented);
	}
	
	private ExpandablePanel(Component visibleComponent, String collapsedName, String expandedName, Component panel, boolean expanded, boolean indented){
		super();

		if (visibleComponent != null){
			this.description = visibleComponent;
		}
		else{
			this.description = new JLabel(collapsedName);
			if (expandedName != null){
				this.collComp = collapsedName;
				this.exComp = expandedName;
				this.changeHeading = true;
			}
		}
		this.expandPanel = panel;
		initializePanel(indented);
		if (expanded) {
			expandPanel();
		} 
		else {
			foldPanel();
		}
	}

	public void setBorderColor(Color color) {
		setBorder(new LineBorder(color));
	}

	private void initializePanel(boolean indented) {
		setBorderColor(Color.BLACK);
		setAlignmentX(LEFT_ALIGNMENT);

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		setLayout(gridBagLayout);

		// add expand button
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		expandButtonColor = new Color(getBackground().getRGB());
		expandButton = createExpandButton();
		gridBagLayout.setConstraints(expandButton, gridBagConstraints);
		add(expandButton);

		// add description component
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 1;
		gridBagLayout.setConstraints(description, gridBagConstraints);
		add(description);
		description.addComponentListener(this);

		// add panel
		expandPanel.setVisible(expanded);

		gridBagConstraints.gridy = 1;
		if (indented) {
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridwidth = 1;
		} else {
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 2;
		}
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(4, 4, 4, 4);
		gridBagLayout.setConstraints(expandPanel, gridBagConstraints);
		add(expandPanel);
		expandPanel.addComponentListener(this);
	}

	private JButton createExpandButton() {
		JButton button = new JButton();

		Dimension dim = new Dimension(10, 10);
		
		if (expandIcon != null){
			dim = new Dimension(expandIcon.getIconWidth(), expandIcon.getIconHeight());
		}
		
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		button.setPreferredSize(dim);
		button.setBorderPainted(false);
		button.setBackground(expandButtonColor);
		button.addActionListener(this);

		return button;
	}

	public void expandPanel() {
		expandButton.setToolTipText(FOLDDESCRIPTION);
		expandButton.setIcon(foldIcon);

		expanded = true;
		expandPanel.setVisible(true);
		signalExpanded();

		if(changeHeading)
			((JLabel)description).setText(exComp);
		
		updatePanel();
	}

	public void foldPanel() {
		expandButton.setToolTipText(EXPANDDESCRIPTION);
		expandButton.setIcon(expandIcon);

		expanded = false;
		expandPanel.setVisible(false);
		signalFolded();

		if(changeHeading)
			((JLabel)description).setText(collComp);
		
		updatePanel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (expanded) {
			foldPanel();
		} else {
			expandPanel();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		updatePanel();
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	private void updatePanel() {
		Component root = SwingUtilities.getRoot(this);
		if (root != null) {
			invalidate();

			SwingUtilities.getRoot(this).validate();
		}
	}

	@Override
	public void invalidate() {
		if (expanded) {
			int width = getPreferredSize().width;
			int height = Math.max(MINHEIGHT, description.getPreferredSize().height + expandPanel.getPreferredSize().height + 10);

			setPreferredSize(new Dimension(width, height));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		} else {
			int width = getPreferredSize().width;
			int height = Math.max(MINHEIGHT, description.getPreferredSize().height + 4);
			setPreferredSize(new Dimension(width, height));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		}

		super.invalidate();
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);

		if (expandButton != null) {
			expandButtonColor = new Color(bg.getRed(), bg.getGreen(), bg.getBlue());
			expandButton.setBackground(expandButtonColor);

			description.setBackground(bg);
		}
	}

}
