package slivisu.data.selection;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import slivisu.gui.controller.InteractionController;
import slivisu.gui.controller.ThreadGenerator;
import slivisu.gui.controller.WaitForThread;

/**
 * enthält selektierte Daten als Collection vom Typ E
 * @author unger
 *
 * @param <E>
 */
public class Selection<E> implements ThreadGenerator {

	protected Collection<E> selected;

	private InteractionController controller = null;
	private boolean changed;
	private Collection<SelectionListener<E>> sharers;

	public Selection(){
		this.selected = new Vector<E>();
		this.changed = false;
		this.sharers = new Vector<SelectionListener<E>>();
	}

	/**
	 * holt alle Elemente der Selektion
	 * @return
	 */
	public Collection<E> getAll() {
		return selected;
	}

	/**
	 * ersetzt die bisherige Selektion
	 * @param items
	 */
	public synchronized void set(Collection<E> items) {
		this.selected = items;
		notifyListeners();
	}

	/**
	 * ergaenzt die bisherige Selektion
	 * @param items
	 */
	public synchronized void addAll(Collection<E> items){
		this.selected.addAll(items);
		notifyListeners();
	}

	/**
	 * entfernt alle Elemente aus der Selektion
	 * @param items
	 */
	public synchronized void removeAll(Collection<E> items){
		this.selected.removeAll(items);
		notifyListeners();
	}

	/**
	 * fuegt ein einzelnes Element zur Selektion dazu
	 * @param item
	 */
	public synchronized void add(E item){
		this.selected.add(item);
		notifyListeners();
	}

	/**
	 * entfernt ein einzelnes Element
	 * @param item
	 */
	public synchronized void remove(E item){
		this.selected.remove(item);
		notifyListeners();
	}

	/**
	 * entfernt alle Elemente aus der Selektion
	 */
	public synchronized void clear(){
		this.selected.clear();
		notifyListeners();
	}

	/**
	 * @param object
	 * @return true Element ist enthalten, false Element ist nicht enthalten
	 */
	public boolean contains(E object){
		return selected.contains(object);
	}

	public boolean isEmpty(){
		return selected.isEmpty();
	}

	public synchronized void filter(Collection<E> filter){
		List<E> filteredSelection = new Vector<E>();
		for (E id : getAll()){
			if (filter.contains(id)){
				filteredSelection.add(id);
			}
		}
		set(filteredSelection);
	}


	public void setController(InteractionController controller){
		this.controller = controller;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void addSharer(SelectionListener<E> sharer){
		this.sharers.add(sharer);
	}

	public void notifyListeners(){
		changed = true;

		WaitForThread threads = new WaitForThread(this);

		for (SelectionListener<E> l : sharers){
			Thread t = new SelectionListenerThread<E>(controller, this, l);
			threads.add(t);
			t.start();
		}

		Thread t = new Thread(threads);
		t.start();		
	}

	@Override
	public void finish() {
	}
}
