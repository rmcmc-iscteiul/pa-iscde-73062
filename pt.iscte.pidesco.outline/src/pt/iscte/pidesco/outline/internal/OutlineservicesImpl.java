package pt.iscte.pidesco.outline.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.outline.service.OutlineListener;
import pt.iscte.pidesco.outline.service.OutlineServices;

public class OutlineservicesImpl implements OutlineServices{
	private JavaEditorServices je;
	private Scanner s;
	private ArrayList <String> toDisplay = new ArrayList <String>();
	


	public void addListener(OutListener listener) {
		OutlineActivator.getInstance().addListener(listener);
		
	}

	public void removeListener(OutListener listener) {
		OutlineActivator.getInstance().removeListener(listener);
		
	}

	@Override
	public void highlightText() {
		
		
	}
	
	
	



	
}
