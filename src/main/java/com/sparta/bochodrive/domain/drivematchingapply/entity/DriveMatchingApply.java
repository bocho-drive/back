package com.sparta.bochodrive.domain.drivematchingapply.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DriveMatchingApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
