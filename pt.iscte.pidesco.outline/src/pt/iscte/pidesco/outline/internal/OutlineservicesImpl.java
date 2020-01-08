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
import pt.iscte.pidesco.outline.service.OutlineServices;
import java.util.List;

public class OutlineservicesImpl implements OutlineServices{

	private static final String EXT_POINT_HIGHLIGHT = "pt.iscte.pidesco.highlight";
	
	public  OutlineservicesImpl () {
		
	}
	

	public  OutlineservicesImpl (String methodname) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
        for(IExtension ext : reg.getExtensionPoint(EXT_POINT_HIGHLIGHT).getExtensions()) {
            OutlineServices outlineServices = null;
            try {
                outlineServices = (OutlineServices) ext.getConfigurationElements()[0].createExecutableExtension("class");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(outlineServices != null ) {
               outlineServices.highlightText(methodname);
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
