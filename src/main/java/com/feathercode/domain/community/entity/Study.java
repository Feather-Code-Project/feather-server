package com.feathercode.domain.community.entity;

import com.feathercode.domain.model.State;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@DiscriminatorValue("STUDY")
@Entity
public class Study extends Community {
    @Enumerated(EnumType.STRING)
    private State state;
}
