package cn.synway.talk.grpcclient.client;

import java.util.concurrent.TimeUnit;

import cn.synway.talk.grpc.proto.GreeterGrpc;
import cn.synway.talk.grpc.proto.HelloReply;
import cn.synway.talk.grpc.proto.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.okhttp.OkHttpChannelBuilder;

public class HelloWorldClient {

	private final String host = "localhost";
	private final int port = 50050;
	private ManagedChannel channel;
	private GreeterGrpc.GreeterBlockingStub blockingStub;

	public void activate() {
		channel = OkHttpChannelBuilder.forAddress(host, port).usePlaintext().build();
		blockingStub = GreeterGrpc.newBlockingStub(channel);
		try {
			greet("world");
			shutdown();
		} catch (InterruptedException ex) {
		}
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/**
	 * Say hello to server.
	 */
	public void greet(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response;
		try {
			response = blockingStub.sayHello(request);
			System.out.println(response.getMessage());
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
			return;
		}
	}
}
