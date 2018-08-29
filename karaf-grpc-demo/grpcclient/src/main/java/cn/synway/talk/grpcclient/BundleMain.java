package cn.synway.talk.grpcclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cn.synway.talk.grpcclient.client.HelloWorldClient;

public class BundleMain implements BundleActivator {
	@Override
	public void start(BundleContext context) {
		HelloWorldClient hwc = new HelloWorldClient();
		hwc.activate();
	}

	@Override
	public void stop(BundleContext context) {
	}
}
