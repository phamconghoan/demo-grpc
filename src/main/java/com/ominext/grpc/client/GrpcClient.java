package com.ominext.grpc.client;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import com.ominext.grpc.demo.HelloRequest;
import com.ominext.grpc.demo.HelloResponse;
import com.ominext.grpc.demo.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.Nullable;

public class GrpcClient {
    static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext(true)
            .build();

    public static void demoSyncHello() {
        HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("FirstName")
                .setLastName("LastName")
                .build());

        System.out.println();

    }

    public static void demoAsyncHello() {
        HelloServiceGrpc.HelloServiceFutureStub stub = HelloServiceGrpc.newFutureStub(channel);

        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("FirstName")
                .setLastName("LastName")
                .build();

        ListenableFuture<HelloResponse> response = stub.hello(request);
        Futures.addCallback(response, new FutureCallback<HelloResponse>() {
            public void onSuccess(@Nullable HelloResponse empty) {
                System.out.println("deleteMeetingEventsRequest -> onCompleted()");
            }

            public void onFailure(Throwable t) {
                System.out.println("deleteMeetingEventsRequest -> onError():" + t.getMessage());
                // TODO
            }
        });
    }

    public static void main(String[] args) {
        // Demo 1: Call sync
        demoSyncHello();

        // Demo 2: Call async
        //demoAsyncHello();

        //channel.shutdown();

    }
}
