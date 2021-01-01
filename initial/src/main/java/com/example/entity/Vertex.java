package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
class Vertex {
    String label;
    Vertex(String label) {

        this.label = label;
    }
}
