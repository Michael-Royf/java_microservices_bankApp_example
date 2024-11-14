package com.michael.bankingAccounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class BaseEntity {
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(insertable = false, name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @CreatedBy
    @Column(updatable = false, name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false, name = "updated_by")
    private String updatedBy;


//    @Id
//    // @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize =1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
//    @Column(name = "id", updatable = false)
//    private Long id;
//    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();


}
