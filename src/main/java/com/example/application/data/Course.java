package com.example.application.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseName;
    private String day;
    private String time;
    private String price;
    private String courseDescription;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
