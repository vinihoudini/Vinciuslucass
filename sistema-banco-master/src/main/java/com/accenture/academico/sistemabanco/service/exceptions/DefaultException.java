package com.accenture.academico.sistemabanco.service.exceptions;


//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;



public class DefaultException extends RuntimeException {


    @Override
     public synchronized Throwable fillInStackTrace(){
		return this;
		
	}

    public DefaultException(String name, Exception e) {
	super(name, e);
	
		
	}
	
	public DefaultException(String name) {
		super(name);
		
		
		
	}

    public  DefaultException(Integer id) {
		
    }

	//public ResponseEntity<String> getResponseEntityDemo(){
	//	return new ResponseEntity<>("This is the body", HttpStatus.OK);
		//return ResponseEntity.notFound().build();
	//}
    //return ResponseEntity.notFound().build();
    
}