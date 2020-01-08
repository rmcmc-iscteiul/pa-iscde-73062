package pt.iscte.pidesco.outline.internal;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyleConstants;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.search.internal.SearchServicesImpl;
import pt.iscte.pidesco.search.service.SearchServices;

public class OutlineView implements PidescoView{

	public static final String VIEW_ID = "pt.iscte.pidesco.outline.tree";	

	private List <No> nos;
	private static OutlineView instance;

	private TreeViewer tree;
	private Text text;

	private Image methodIcon;
	private Image classIcon;
	private Image attributeIcon;
	private Image constructorIcon;

	public OutlineView() {
		instance = this;

	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		methodIcon = imageMap.get("var_declaration_obj.gif");
		classIcon = imageMap.get("class.gif");
		attributeIcon = imageMap.get("field_private_obj.gif");
		constructorIcon = imageMap.get("constr_ovr.png");
		
		tree = new TreeViewer(viewArea);
		tree.setContentProvider(new ViewContent());
		tree.setLabelProvider(new ViewLabelProvider());

		tree.addPostSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();

				No no = (No) selection.getFirstElement();
				if(no != null) {
					int offset = no.getOffset();
					int lenght = no.getLenght();
					File fileaberto = OutlineActivator.getInstance().getServico().getOpenedFile();
					OutlineActivator.getInstance().getServico().selectText(fileaberto, offset, lenght);
				}

			}
		});
		
		text = new Text(viewArea, StyleConstants.ALIGN_LEFT);
		
		try {
			atualizar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	boolean isCreated() {
		return text != null;
	}

	public void atualizar() throws IOException {
		File fileaberto = OutlineActivator.getInstance().getServico().getOpenedFile();
		if (fileaberto != null && tree != null) {
			FileReaderAST fr = new FileReaderAST(fileaberto);
			System.out.println(OutlineActivator.getInstance().getServico().getOpenedFile());
			nos = fr.getNos();			
			tree.setInput(nos);
			
			Tree t = tree.getTree();
			for (TreeItem ti : t.getItems()) {
				if (ti.getBackground().equals(new Color (null, 0,255,255))){
					SearchServices ss = new SearchServicesImpl();
					List <String> lista = ss.getMethodLines();
					for (int i = 0; i < lista.size(); i++) {
						String [] sub1 = lista.get(i).split(" - ");
						String methodname = sub1[1];
						if (ti.getText().contains(methodname)) {
							String [] sub2 = lista.get(i).split("::");
							String line = sub1[1];
							text.setText(line);
						}
					}
				}
			}
			
		}
		
		

	}

	public TreeViewer getTree() {
		return tree;
	}

	public void setTree(TreeViewer tree) {
		this.tree = tree;
	}
	
	public Text getTextField() {
		return text;
	}

	public static OutlineView getInstance () {
		return instance;
	}


	private static class ViewContent implements IStructuredContentProvider, ITreeContentProvider{

		private static final Object[] EMPTY = new Object[0];

		@Override
		public Object[] getChildren(Object parent) {
			No parente = (No) (parent);
			return parente.getFilhos().toArray();
		}
		@Override
		public Object getParent(Object child) {
			No filho = (No) (child);
			return filho.getParent();
		}

		@Override
		public boolean hasChildren(Object parent) {
			No no = (No)(parent);
			return (no.getFilhos().size()>0);
		}
		@Override
		public Object[] getElements(Object inputElement) {
			List<No> nos = (List<No>) inputElement;
			Collections.sort(nos, new ComparadorTipos());
			return nos.toArray();
		}



	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			No no = (No) obj;
			if (no.getTipo().equals("classe")) {
				return no.getName();
			}else {
				return no.getName() + " : " + no.getReturn_tipe();
			}

		}
		public Image getImage(Object obj) {
			No no = (No) obj;
			if (no.getTipo().equals("classe")){
				return classIcon;
			}else if(no.getTipo().equals("atributo")){
				return attributeIcon;
			}
			else if(no.getTipo().equals("método")) {
				return methodIcon;
			}else if(no.getTipo().equals("construtor")) {
				return constructorIcon;
			}
			return null;
		}
	}
}