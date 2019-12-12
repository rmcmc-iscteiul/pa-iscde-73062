package pt.iscte.pidesco.outline.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FileReaderAST {

	private CompilationUnit cu;
	private ArrayList <String> metodos;
	private ArrayList <String> atributos;
	private HashMap <ASTNode, No> nos;
	private List<No> fin;
	private File file;
	private static final String PARAGRAPH_SPLIT_REGEX = "(^\\s{4})";

	public FileReaderAST(File file) throws IOException {
		this.file = file;
		nos = new HashMap<ASTNode, No>();

		metodos = new ArrayList <>();
		atributos = new ArrayList <>();

		if (this.file!= null) {
			start();
		}

	}

	public static String getFileContent(String filepath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line!=null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		return sb.toString();

	}

	public void start () throws IOException {

		if (file!= null) {
			String filePath = file.getPath();

			ASTParser parser = ASTParser.newParser(AST.JLS3);


			char[] filecontent = getFileContent(filePath).toCharArray();

			parser.setSource(filecontent);

			cu = (CompilationUnit) parser.createAST(null);

			cu.accept(new ASTVisitor() {

				public boolean visit(FieldDeclaration node) {
					// loop for several variables in the same declaration
					for(Object o : node.fragments()) {
						VariableDeclarationFragment var = (VariableDeclarationFragment) o;
						String name = var.getName().toString();
						if (nos.isEmpty()) {
							nos.put(node, new No(name, "atributo", null, node.getStartPosition(), node.getLength(), node.getType().toString()));
						}
						else {
							nos.put(node, new No(name, "atributo", nos.get(node.getParent()),node.getStartPosition(), node.getLength(), node.getType().toString()));
						}
						atributos.add(name.toString());
					}
					return false; // false to avoid child VariableDeclarationFragment to be processed again
				}

				public boolean visit (MethodDeclaration node) {
					String name = node.getName().toString();
					System.out.println(node.getReturnType2());
					if (nos.isEmpty()) {
						if (node.isConstructor()) {
							nos.put(node, new No(name, "construtor", null, node.getStartPosition(), node.getLength(), node.getReturnType2().toString()));
							System.out.println(node.getParent());

						}else {
							nos.put(node, new No(name, "método", null, node.getStartPosition(), node.getLength(), node.getReturnType2().toString()));
						}
					}
					else {
						if (node.isConstructor()) {
							nos.put(node, new No(name, "construtor", nos.get(node.getParent()),node.getStartPosition(), node.getLength(), node.getReturnType2().toString()));

						}else {
							nos.put(node, new No(name, "método", nos.get(node.getParent()), node.getStartPosition(), node.getLength(), node.getReturnType2().toString()));				}

					}
					return false;

				}

				@Override
				public boolean visit(TypeDeclaration node) {
					String name = node.getName().toString();
					nos.put(node, new No(name, "classe", null, node.getStartPosition(), node.getLength(), ""));
					return super.visit(node);
				}

			});
		}
		

	}



	public List<No> getNos() {
		fin = new ArrayList<>(nos.values());
		return fin;
	}



}
