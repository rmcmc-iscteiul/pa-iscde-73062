package pt.iscte.pidesco.outline.internal;

import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.outline.extensibility.GetStringHighlight;
import pt.iscte.pidesco.outline.service.OutlineServices;
import java.util.List;

public class OutlineservicesImpl implements OutlineServices{

	private static final String EXT_POINT_HIGHLIGHT = "pt.iscte.pidesco.outline.highlight";

	public  OutlineservicesImpl () {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_HIGHLIGHT).getExtensions()) {
			GetStringHighlight getStringHighlight = null;
			try {
				getStringHighlight = (GetStringHighlight) ext.getConfigurationElements()[0].createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(getStringHighlight != null ) {
				highlightText(getStringHighlight.getStringHighlightText());
			}
		}
	}


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

		if(!(methodname == null)) {
			OutlineView outlineview = OutlineView.getInstance();
			if (outlineview != null) {
				TreeViewer t = OutlineView.getInstance().getTree();
				Tree tree = t.getTree();

				for (TreeItem ti : tree.getItems()) {
					if (ti.getText().contains(methodname)) {
						ti.setBackground(new Color(null, 0,255,255));
					}
				}
			}

		}



	}







}
