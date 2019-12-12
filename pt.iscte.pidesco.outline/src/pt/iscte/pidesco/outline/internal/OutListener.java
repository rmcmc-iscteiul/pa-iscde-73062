package pt.iscte.pidesco.outline.internal;

import java.io.File;
import java.io.IOException;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;

public class OutListener implements JavaEditorListener{


	@Override
	public void fileOpened(File file) {
		try {
			OutlineView.getInstance().atualizar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void fileSaved(File file) {
		try {
			OutlineView.getInstance().atualizar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
