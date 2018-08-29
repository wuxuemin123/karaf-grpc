package cn.synway.talk.grpc;

import java.io.IOException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cn.synway.talk.grpc.server.HelloWorldServer;

public class BundleMain implements BundleActivator {
	@Override
	public void start(BundleContext context) {
		HelloWorldServer hws = new HelloWorldServer();
		try {
			hws.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(BundleContext context) {
	}
}
