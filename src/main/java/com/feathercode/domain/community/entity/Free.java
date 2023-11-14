package com.feathercode.domain.community.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("FREE")
@Entity
public class Free extends Community{
}
