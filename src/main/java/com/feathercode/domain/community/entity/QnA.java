package com.feathercode.domain.community.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("QnA")
@Entity
public class QnA extends Community {

}
