package pt.iscte.pidesco.outline.internal;

import java.util.Comparator;
import java.util.List;

public class ComparadorTipos implements Comparator<No>{

	@Override
	public int compare(No o1, No o2) {
		if (o1.getTipo().equals("classe") && o2.getTipo().equals("método")) {
			return -1;
		}else if(o1.getTipo().equals("classe") && o2.getTipo().equals("atributo")) {
			return -1;
		}else if(o1.getTipo().equals("classe") && o2.getTipo().equals("construtor")) {
			return -1;
		}else if(o1.getTipo().equals("atributo") && o2.getTipo().equals("construtor")) {
			return -1;
		}else if(o1.getTipo().equals("atributo") && o2.getTipo().equals("método")) {
			return -1;
		}else if(o1.getTipo().equals("construtor") && o2.getTipo().equals("método")) {
			return -1;
		}
			return 0;
		
	}
}
