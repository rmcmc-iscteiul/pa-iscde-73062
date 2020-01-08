package pt.iscte.pidesco.outline.service;

import pt.iscte.pidesco.outline.internal.OutListener;

public interface OutlineServices {

	/**
	 * Add an Outline Listener.
	 * @param listener 
	 */
	void addListener(OutListener listener);
	
	
	/**
	 * Remove an Outline Listener.
	 * @param listener
	 */
	void removeListener(OutListener listener);
	
	
	/**
	 * This function is meant to interact with the search plugin, given a String that the user inserts on the search window if it has any correspondencies in the open outline tree it will be highlighted.
	 * @param methodname method name to highlight.
	 */
	void highlightText(String methodname);
	
	
}
