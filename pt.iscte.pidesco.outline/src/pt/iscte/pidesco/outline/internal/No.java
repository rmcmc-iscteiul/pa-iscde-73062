package pt.iscte.pidesco.outline.internal;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Type;

public class No {

	private String name;
	private No parent;
	private ArrayList<No> filhos;
	private String tipo;
	private String return_tipe;
	private int offset;
	private int lenght;
	
	public No (String name, String tipo, No parent, int offset, int lenght, String return_type) {
		this.name = name;
		this.tipo = tipo;
		this.parent = parent;
		this.offset = offset;
		this.lenght = lenght;
		this.return_tipe = return_type;
		filhos = new ArrayList<>();
	}
	
	
	public String getReturn_tipe() {
		return return_tipe.toString();
	}


	public int getOffset() {
		return offset;
	}


	public int getLenght() {
		return lenght;
	}


	public String getName() {
		return name;
	}


	public String getTipo() {
		return tipo;
	}


	public No getParent() {
		return parent;
	}


	public ArrayList<No> getFilhos() {
		return filhos;
	}

	public void addFilho (No filho) {
		filhos.add(filho);
	}
	
	public void setFilhos(ArrayList<No> filhos) {
		this.filhos = filhos;
	}
	
}
