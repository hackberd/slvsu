package slivisu.view.table;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;

public class TableSelectionController implements MouseListener, KeyListener {

	private SelectionTableModel tableModel;
	private JTable table;

	private List<Integer> selectedRows;
	

	public TableSelectionController(JTable table, JTable leftTable, SelectionTableModel tableModel){
		this.table = table;
		this.tableModel = tableModel;
		this.table.addMouseListener(this);
		this.table.addKeyListener(this);
		leftTable.addMouseListener(this);
		leftTable.addKeyListener(this);
		this.selectedRows = new Vector<Integer>();
	}

	public void setSelectedRowsToView(List<Integer> rows) {

			table.clearSelection();
			for (int id : rows) {
				int row = table.convertRowIndexToView(id);
				table.addRowSelectionInterval(row, row);
			}
	}

	public void setSelectedRowsToModel(){
		int[] viewRows = table.getSelectedRows();
		List<Integer> rows = new Vector<Integer>();
		for (int i = 0; i < viewRows.length; i++) {
			int id = table.convertRowIndexToModel(viewRows[i]);
			rows.add(id);
		}
		if (selectedRows.size() != rows.size() || ! selectedRows.containsAll(rows)){
			selectedRows = rows;
			tableModel.setSelectedRows(selectedRows);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (! arg0.isControlDown() && ! arg0.isShiftDown()){
			setSelectedRowsToModel();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (! arg0.isControlDown() && ! arg0.isShiftDown()){
			setSelectedRowsToModel();
		}
	}
}
