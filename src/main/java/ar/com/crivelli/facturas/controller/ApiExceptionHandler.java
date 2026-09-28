package ar.com.crivelli.facturas.controller;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler { @ExceptionHandler({NoSuchElementException.class,IllegalStateException.class}) ResponseEntity<Map<String,String>> manejar(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
