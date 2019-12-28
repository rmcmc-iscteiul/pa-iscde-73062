package pt.iscte.pidesco.outline.internal;

import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.outline.service.OutlineServices;
import java.util.List;

public class OutlineservicesImpl implements OutlineServices{
	

	@Override
	public void addListener(OutListener listener) {
		OutlineActivator.getInstance().addListener(listener);
		
	}
	
	@Override
	public void removeListener(OutListener listener) {
		OutlineActivator.getInstance().removeListener(listener);
		
	}


	@Override
	public void highlightText(String methodname) {

		TreeViewer t = OutlineView.getInstance().getTree();
		Tree tree = t.getTree();
		
		for (TreeItem ti : tree.getItems()) {
			if (ti.getText().contains(methodname)) {
				ti.setBackground(new Color(null, 0,0,255));
			}
		}
		
		
	}
	
	
	



	
}
