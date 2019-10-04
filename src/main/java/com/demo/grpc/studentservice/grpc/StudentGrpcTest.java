package com.demo.grpc.studentservice.grpc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.grpc.dao.student.service.Student;
import com.grpc.dao.student.service.StudentStatusRequest;
import com.grpc.dao.student.service.client.ServiceStudentDaoClient;

import io.grpc.stub.CallStreamObserver;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import io.grpc.stub.StreamObserver;

public class StudentGrpcTest {


   @Test
   public void test(){
      ServiceStudentDaoClient client = new ServiceStudentDaoClient();
      List<Student> list = client.findAll();
      System.out.println(list);
   }

  /* @Test
   public void test2() throws InterruptedException {
      ServiceStudentDaoClient client = new ServiceStudentDaoClient();
      List<Student> list = new ArrayList<>();

       ClientResponseObserver<StudentStatusRequest, Student> streamObserver = new ClientResponseObserver<StudentStatusRequest, Student>() {

           @Override public void beforeStart(ClientCallStreamObserver<StudentStatusRequest> outboundStream) {
               outboundStream.disableAutoInboundFlowControl();
           }
           @Override public void onNext(Student student) {
               System.out.println("Async client onNext:" + student);
           }

           @Override public void onError(Throwable throwable) {}

           @Override public void onCompleted() { System.out.println("Call completed!!!"); }
       };

       CallStreamObserver<StudentStatusRequest> observer = client.findAllByStatus(streamObserver);

       observer.onNext(StudentStatusRequest.newBuilder().setStatus(true).build());
       observer.request(1);
       try {
           Thread.sleep(6000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       observer.request(1);
   }*/


}

