package com.shreyas.spring_boot_advanced_template.entity;

import com.shreyas.spring_boot_advanced_template.entity.Constants.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    @NotNull
    private Boolean isEnabled;
}
