package com.example.labb2_spring_boot.exeptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private Long id;
    private String type;

    public EntityNotFoundException(String type) {
        super(STR."No \{type}(s) found ");
        this.type = type;
    }

    public EntityNotFoundException(String type, Long id) {
        super(STR. "\{type} with id:\{ id } not found" );
        this.id = id;
        this.type = type;
    }

}
