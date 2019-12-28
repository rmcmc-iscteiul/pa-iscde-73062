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
	 * 
	 * @param methodname method name to highlight.
	 */
	void highlightText(String methodname);
	
	
}
