package pa.javafilescanner;
import java.io.File;
import java.util.ArrayList;


public class JavaFileScanner {

	private File basedir;

	public JavaFileScanner(String path) {
		basedir = new File(path);
		assert basedir.exists() && basedir.isDirectory();
	}

	public void accept(JavaFileVisitor v) throws ClassNotFoundException {
		PackageStack stack = new PackageStack();
		visit(basedir, v, stack);
	}

	private static void visit(File dir, JavaFileVisitor v, PackageStack stack) throws ClassNotFoundException {

		// for every package v.visitPackage(...) should be invoked 
		if (!dir.isDirectory()) {
			Class classe = Class.forName(stack.packageName() + "."+ dir.getName().substring(0,dir.getName().length()-5));
			v.visit(classe);
		}else {
			stack.push(dir.getName());
			v.visitPackage(dir.getName());
			for (File f : dir.listFiles()) {
				visit(f,v,stack);
			}
			stack.pop();
		}
		// for every class v.visitClass(...) should be invoked
		// dynamic class loading:
		// Class<?> clazz = Class.forName("pa.students.Student");

	}


	private static class PackageStack {
		private ArrayList<String> stack = new ArrayList<>();

		public void push(String e) {
			stack.add(e);
		}

		public String pop() {
			return stack.remove(stack.size()-1);
		}

		public boolean isDefault() {
			return stack.isEmpty();
		}

		public String packageName() {
			return String.join(".", stack);
		}
	}

}
