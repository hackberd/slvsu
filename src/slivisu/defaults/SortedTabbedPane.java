package slivisu.defaults;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SortedTabbedPane<E extends JComponent> extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, E> components;
	private Map<String, JPanel> panels;
	private Map<String, Boolean> visibleComponents;
	private List<String> sortedComponents;
	
	public SortedTabbedPane(){
		this.components = new HashMap<String, E>();
		this.panels = new HashMap<String, JPanel>();
		this.visibleComponents = new HashMap<String, Boolean>();
		this.sortedComponents = new Vector<String>();
	}
	
	public void updateTabbedPane() {
		
		for (String name : sortedComponents){
			//JComponent component = components.get(name);
						
			if (visibleComponents.get(name) == null || ! visibleComponents.get(name)){
				// entferne leere Tabelle
				int index = -1;
				for (int i=0; i < getTabCount(); i++){
					if (panels.get(name).equals(getComponentAt(i))){
						index = i;
					}
				}
				if (index != -1){
					remove(panels.get(name));
				}
			}
			else{
				// füge hinzu, sofern vorhanden
				int index = -1;
				for (int i=0; i < getTabCount(); i++){
					if (panels.get(name).equals(getComponentAt(i))){
						index = i;
					}
				}
				if (index == -1){
					// füge sortiert ein
					index = -1;
					for (int i=0; i < getTabCount(); i++){
						String currentName = getTitleAt(i).toLowerCase().trim();
						if (name.toLowerCase().trim().compareTo(currentName) < 0){
							index = i;
							break;
						}
					}
					if (index == -1){
						index = getTabCount();
					}
					insertTab(name, null, panels.get(name), null, index);
				}
			}
		}
	}
	
	public void addComponent(String componentName, E component, JPanel panel){
		this.components.put(componentName, component);
		this.panels.put(componentName, panel);
		this.sortedComponents.add(componentName);
		Collections.sort(sortedComponents);
	}
	
	public E getComponent(String componentName){
		return components.get(componentName);
	}
	
	public Map<String, E> getComps(){
		return components;
	}
	
	public void setComponentVisible(String componentName, boolean visible){
		this.visibleComponents.put(componentName, visible);
	}
}
