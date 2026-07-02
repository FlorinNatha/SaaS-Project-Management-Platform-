package com.saasplatform.task.dto;

import com.saasplatform.task.model.Task;
import com.saasplatform.task.model.TaskStatus;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class TaskBoardResponse {
    private Long projectId;
    private Map<TaskStatus, List<Task>> columns;
}
