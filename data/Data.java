package slivisu.data;

import java.util.Collection;
import java.util.Map;

import slivisu.data.datatype.Bin;
import slivisu.data.selection.Selection;
import slivisu.data.selection.SelectionListener;
import slivisu.gui.controller.DataInterface;
import slivisu.gui.controller.InteractionController;
import slivisu.gui.globalcontrols.timescale.TimeScaleData;

/**
 * das Haupt-Datenobjekt in Slivisu mit allen global verfügbaren Datenstrukturen und Selektionen 
 * 
 * @author unger
 *
 */
public class Data implements DataInterface, SelectionListener<Sample>{

	private InteractionController controller;
	
	private ObservationData observations;
	
	private ObservationTableData observationsTD;

	private Selection<Sample> allSamples; 
	private Selection<Sample> selectedSamples;
	private Selection<Sample> markedSamples;

	private TimeRange timeRange;
	
	// ################################# Getter und Setter #################################

	public Data(InteractionController controller) {

		this.controller = controller;
		this.controller.setDataInterface(this);

		this.allSamples = new Selection<Sample>();
		this.selectedSamples = new Selection<Sample>();
		this.markedSamples = new Selection<Sample>();

		this.allSamples.setController(controller);
		this.selectedSamples.setController(controller);
		this.markedSamples.setController(controller);

		this.allSamples.addSharer(this);
		this.selectedSamples.addSharer(this);
		this.markedSamples.addSharer(this);

		this.observations = new ObservationData();
		this.observationsTD = new ObservationTableData();
		
		Bin<Integer> ageRange = observations.getTimeRange(allSamples.getAll());
		this.timeRange = new TimeRange(this, ageRange.getMin(), ageRange.getMax());
	}
	
	@Override
	public void updateData(Selection<Sample> selection) {
		//--------------------------------------------------
		if (selection == allSamples){
			Collection<Sample> selectedSamples = allSamples.getAll();
			if (timeRange.isFreezeRange()){
				selectedSamples = observations.filterByTimeRange(selectedSamples, timeRange.getMin(), timeRange.getMax());				
			}
			else{
				Bin<Integer> tmp = observations.getTimeRange(allSamples.getAll());
				timeRange.setGlobalMin(tmp.getMin());
				timeRange.setGlobalMax(tmp.getMax());
				timeRange.setMin(tmp.getMin());
				timeRange.setMax(tmp.getMax());
			}	
			this.selectedSamples.set(selectedSamples);
		}
		//--------------------------------------------------	
		if (selection == selectedSamples){
			this.markedSamples.filter(selectedSamples.getAll());
		}
		//--------------------------------------------------
		if (selection == markedSamples){
			controller.receive();
		}
	}

	@Override
	public void wrapUp() {
		selectedSamples.setChanged(false);
		markedSamples.setChanged(false);
	}

	public void addObservationData(Map<Sample, Map<Zeitscheibe, Double>> fundDatierung){
		this.observations.addFundDatierungen(fundDatierung);
		Collection<Sample> samples = this.observations.getSamples();
		allSamples.set(samples);
	}

	public void updateSelections() {
		Collection<Sample> selectedIds = allSamples.getAll();
		selectedIds = observations.filterByTimeRange(selectedIds, timeRange.getMin(), timeRange.getMax());				
		this.selectedSamples.set(selectedIds);
	}

	// ################################# Getter #################################

	public ObservationData getObservationData() {
		return observations;
	}
	
	public Selection<Sample> getSelectedSamples() {
		return selectedSamples;
	}

	public Selection<Sample> getMarkedSamples() {
		return markedSamples;
	}

	public TimeScaleData getTimeRange() {
		return timeRange;
	}

	public ObservationTableData getObservationTableData() {
		return observationsTD;
	}
}