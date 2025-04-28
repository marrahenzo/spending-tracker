package io.github.marrahenzo.spending_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class DatedEntity implements Serializable {

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
