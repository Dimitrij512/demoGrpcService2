package com.demo.grpc.studentservice.service;

import java.util.List;

import org.lognet.springboot.grpc.GRpcService;

import com.grpc.dao.student.service.Student;
import com.grpc.dao.student.service.StudentStatusRequest;
import com.grpc.dao.student.service.client.ServiceStudentDaoClient;
import com.grpc.student.service.StudentAgeRequest;
import com.grpc.student.service.StudentIdRequest;
import com.grpc.student.service.StudentResponse;
import com.grpc.student.service.StudentServiceGrpc;
import com.grpc.student.service.StudentsResponse;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

@GRpcService
public class StudentService extends StudentServiceGrpc.StudentServiceImplBase {

    private ServiceStudentDaoClient daoClient = new ServiceStudentDaoClient();
    private static final int TIME_OUT = 2000;

    @Override
    public void getStudentById(StudentIdRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("################## call getStudentById ##################");

        Student studentById = daoClient.getById(request.getId());

        responseObserver.onNext(StudentResponse.newBuilder().setStudent(studentById).build());
        responseObserver.onCompleted();

        System.out.println("################## finished work getStudentById ##################");
    }

    @Override
    public void findStudentsByAge(StudentAgeRequest request, StreamObserver<StudentsResponse> responseObserver) {
        System.out.println("################## call findStudentsByAge ##################");

        try {
            //emulation deadline propagation
            System.out.println("Time out:" + TIME_OUT + " ms");
            Thread.sleep(TIME_OUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ServerCallStreamObserver<StudentsResponse> streamObserver = (ServerCallStreamObserver<StudentsResponse>) responseObserver;
        streamObserver.setOnCancelHandler(() -> System.out.println("################## Request was canceled ##################"));

        List<Student> studentList = daoClient.findByAge(request.getAge());

        responseObserver.onNext(StudentsResponse.newBuilder().addAllStudents(studentList).build());
        responseObserver.onCompleted();

        System.out.println("################## finished work findStudentsByAge ##################");
    }

    @Override
    public void findAllStudent(com.google.protobuf.Empty request, StreamObserver<StudentsResponse> responseObserver) {
        System.out.println("################## call findAllStudent ##################");

        List<Student> studentlist = daoClient.findAll();

        responseObserver.onNext(StudentsResponse.newBuilder().addAllStudents(studentlist).build());
        responseObserver.onCompleted();

        System.out.println("################## finished work findAllStudent ##################");
    }

    @Override
    public StreamObserver<StudentStatusRequest> findStudentByStatusStream(StreamObserver<Student> responseObserver){
        System.out.println("###################################################");
        System.out.println("#  **** call findStudentByStatusStream ****       #");
        System.out.println("###################################################");

        return daoClient.findAllByStatus(responseObserver);
    }

}
