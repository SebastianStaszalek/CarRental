package com.capgemini.jstk.carrental.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable<U> {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;
}
