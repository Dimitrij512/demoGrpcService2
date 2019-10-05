package com.demo.grpc.studentservice.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.protobuf.Empty;
import com.grpc.dao.student.service.Student;
import com.grpc.dao.student.service.StudentStatusRequest;
import com.grpc.student.service.StudentAgeRequest;
import com.grpc.student.service.StudentIdRequest;
import com.grpc.student.service.StudentServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

@Component
public class ServiceStudentClient {

    private StudentServiceGrpc.StudentServiceBlockingStub serviceBlockingStub;
    private StudentServiceGrpc.StudentServiceStub stub;

    public ServiceStudentClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6063).usePlaintext().build();

        serviceBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        stub = StudentServiceGrpc.newStub(channel);
    }

    public Student getStudentById(String id) {
        return serviceBlockingStub.getStudentById(StudentIdRequest.newBuilder().setId(id).build()).getStudent();
    }

    // dead line test
    public List<Student> getStudentByAge(int age, int deadline) {
        return serviceBlockingStub.withDeadlineAfter(deadline, TimeUnit.MILLISECONDS).findStudentsByAge(StudentAgeRequest.newBuilder().setAge(age).build()).getStudentsList();
    }

    public List<Student> getAllStudents() {
        return serviceBlockingStub.findAllStudent(Empty.newBuilder().build()).getStudentsList();
    }

    public StreamObserver<StudentStatusRequest> findStudentByStatusStream(StreamObserver<Student> responseObserver) {
        return stub.findStudentByStatusStream(responseObserver);
    }

}
