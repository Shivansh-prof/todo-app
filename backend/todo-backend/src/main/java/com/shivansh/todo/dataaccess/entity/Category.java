package com.shivansh.todo.dataaccess.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
@Data
@Builder
public class Category {
    @Id
    private Long categoryId;
    private String categoryName;
    private Long UserId;
}
