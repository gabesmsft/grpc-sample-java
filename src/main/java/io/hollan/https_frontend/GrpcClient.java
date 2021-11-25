// Modified from https://github.com/grpc/grpc-java/blob/master/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java
package io.hollan.https_frontend;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.hollan.proto.*;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class GrpcClient {
  private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  public GrpcClient() {
    String target = System.getenv().getOrDefault("GRPC_SERVER_ADDRESS", "localhost:50051");
    ManagedChannelBuilder builder = ManagedChannelBuilder.forTarget(target);

    if(target.endsWith(":443")) {
      builder.useTransportSecurity();
    }
    else {
      builder.usePlaintext();
    }

    ManagedChannel channel = builder.build();
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  /** Say hello to server. */
  public String greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return e.toString();
    }
    return response.getMessage();
  }
}