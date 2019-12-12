package pt.iscte.pidesco.outline.internal;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.outline.service.OutlineServices;
import pt.iscte.pidesco.outline.service.ProjectBrowserServices;

public class OutlineActivator implements BundleActivator{
	
	private static OutlineActivator instance;
	private Set<OutListener> listeners;
	private ServiceRegistration<OutlineServices> service;
	private JavaEditorServices servico;
	private ProjectBrowserServices servico2;
	private FileReaderAST fr;
	private BundleContext context;
	private OutListener listener;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		listeners = new HashSet<OutListener>();
		service = context.registerService(OutlineServices.class, new OutlineservicesImpl(), null);
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		servico = context.getService(ref);
		listener = new OutListener();
		servico.addListener(listener);

	}

	public JavaEditorServices getServico() {
		return servico;
	}

	public void setServico(JavaEditorServices servico) {
		this.servico = servico;
	}

	public BundleContext getContext() {
		return context;
	}


	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		service.unregister();
		listeners.clear();
	}

	public static OutlineActivator getInstance() {
		return instance;
	}
	
	
	
	public FileReaderAST getFr() {
		return fr;
	}

	public JavaEditorServices getJavaEditorServices() {
		return servico;
	}
	
	public Set<OutListener> getListeners() {
		return listeners;
	}

	public void addListener(OutListener l) {
		listeners.add(l);
	}

	public void removeListener(OutListener l) {
		listeners.remove(l);
	}

}
