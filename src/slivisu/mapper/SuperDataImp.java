package slivisu.mapper;

import java.util.List;

import slivisu.data.Data;
import slivisu.data.MyZeitscheibe;
import slivisu.view.myviews.SuperData;

public class SuperDataImp implements SuperData {
	Data data;
	public SuperDataImp(Data data) {
		this.data = data;
	}
	
	@Override
	public void updateData() {
		// TODO Auto-generated method stub
	}
	
	

	@Override
	public List<List<MyZeitscheibe>> getAllData() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
