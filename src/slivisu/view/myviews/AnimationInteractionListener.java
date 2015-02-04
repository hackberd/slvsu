package slivisu.view.myviews;

import slivisu.gui.controller.InteractionListener;

public class AnimationInteractionListener implements InteractionListener {
	private AnimationView view;
	public AnimationInteractionListener(AnimationView view) {
		this.view =view;
	}
	
	@Override
	public void updateView() {
		this.view.updateView();
	}

	@Override
	public String getListenerName() {
		return "AnimationInteractionListenerName";
	}

}
