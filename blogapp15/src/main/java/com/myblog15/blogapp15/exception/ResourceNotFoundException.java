package com.myblog15.blogapp15.exception;

import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;
    //PostMan understand If I am calling this constructor there is a super keyword, In super keyword if there is some msg, lets take this msg and display in postman.
    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){//Hre resource name can be Post, Lead, Contact
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));//Here I am displaying customize msg
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}
