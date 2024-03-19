package com.hsw.birdparkmanagement.model.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    @Id
    String m_name;
    String m_type;
    String m_value;
}
