package com.myblog15.blogapp15.exception;

import com.myblog15.blogapp15.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
//ResourceNotFound Was user define class or  custom class other than the custom class if any other exception happen how to handle
@ControllerAdvice //This annotation helps me out firstly. Wherever exception happen in the project spring boot know that exception given to this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {// Whenever exception object is created this exception object pass on to this class. It will further handle the exception.

    //Specific Exception like you are giving wrong id, wrong passport and wrong pan cards number.
    @ExceptionHandler(ResourceNotFoundException.class) // Here we specify which class exception need to handles. Here we handle ResourceNotFound class handle.
    public ResponseEntity<ErrorDetails> handlerResourceNotFoundException(// ResourseNotFoundHandler method is used..
            ResourceNotFoundException exception,
            WebRequest webRequest    //It will treated like ModelMap model. In controller layer take the data ang give it to view.Here we use WebRequest webRequest it will take the data and give it to postman or front end view.
                                                      // What this does it will take that resourcenotfound object address and put that into this reference variable, this act like actually catch block. Imagine exceptional Handler method to act as a catch block and your complete code act like a  try block.
    ){// Whenever you give wrong id, which exception object does it create here we are creating the object of ResourceNotFoundException. I have created custom class exception earlier.
ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));// This going to generate some URI// When exception occur I want to put that information into error details object and return that object.

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }//In your project Whenever ResourceNotFound Object is created this method will be called  public ResponseEntity<ErrorDetails> handlerResourceNotFoundException()

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException( // Here exception Handler method is used.
            Exception exception,
            WebRequest webRequest
    ){// Whenever you give wrong id, which exception object does it create here we are creating the object of ResourceNotFoundException. I have created custom class exception earlier.
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));// This going to generate some URI// When exception occur I want to put that information into error details object and return that object.

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }// Any exception other then ResourceNotFound can be handle here.
}// @ExceptionHandler(ResourceNotFoundException.class)// Here class name (ResourceNotFoundException.class) which is matching to that object. Object which is resourcenotfound object.
// Here the advantage is one file can handle all the exception.
//ErrorDetails will return three things:-
//timestamp,message and details







