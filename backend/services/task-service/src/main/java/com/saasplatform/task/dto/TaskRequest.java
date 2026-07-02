package com.saasplatform.task.dto;

import com.saasplatform.task.model.TaskStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Long projectId;
    private Long assigneeId;
    private TaskStatus status;
    private LocalDateTime dueDate;
}
