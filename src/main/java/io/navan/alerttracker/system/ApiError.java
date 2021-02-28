/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.navan.alerttracker.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author tonybrouwer
 */
public final class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<Error> errors;
 
    public ApiError() {};
    
    public ApiError(HttpStatus httpStatus, String message, List<Error> errors) {
        super();
        this.setHttpStatus(httpStatus);
        this.setMessage(message);
        this.setErrors(errors);
    }
 
    public ApiError(HttpStatus httpStatus, String message, Error error) {
        super();
        this.setHttpStatus(httpStatus);
        this.setMessage(message);
        setErrors(Arrays.asList(error));
    }
 
    public ApiError(HttpStatus httpStatus, String message) {
        super();
        this.setHttpStatus(httpStatus);
        this.setMessage(message);
        setErrors(new ArrayList<>());
    }

    /**
     * Holds the sub-errors that together are delivered for an ApiError. Used mainly for validation errors
     * so that we can return a myriad of errors, one for each property, as necessary.
     * 
     * @author tonybrouwer
     *
     */
    public static class Error {
        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getProperty() {
            return property;
        }

        public void setField(String field) {
            this.property = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        private String entity;      // The entity
        private String property;    // The property of the entity
        private String message;     // The error message
        
        public Error() {}
        
        public Error(String entity, String field, String message) {
            this.entity = entity;
            this.property = field;
            this.message = message;
        }
        
        @Override
        public String toString() {
            return String.format("%s(%s): %s", this.getEntity(), this.getProperty(), this.getMessage());
        }
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("{\nstatus: %d,\nmessage: %s,\nerrors:\t[",
                this.getHttpStatus().value(), this.getMessage()));
        sb.append(
                String.join(",\n", errors.stream()
                .map(Error::toString)
                .toArray(size -> new String[size])));
        sb.append("\n\t]\n}");
        return sb.toString();
    }
    
}
