package cn.synway.talk.grpc.server;

import java.io.IOException;
import java.util.logging.Logger;

import cn.synway.talk.grpc.proto.GreeterGrpc;
import cn.synway.talk.grpc.proto.HelloReply;
import cn.synway.talk.grpc.proto.HelloRequest;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;

public class HelloWorldServer {
	private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

	private Server server;

	public void start() throws IOException {
		/* The port on which the server should run */
		int port = 50051;
		server = NettyServerBuilder.forPort(port).addService(new GreeterImpl()).build();
		server.start();
		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown
				// hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				HelloWorldServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

		@Override
		public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
			HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName() + " hahahahahahaahaha !!!!")
					.build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}
}
