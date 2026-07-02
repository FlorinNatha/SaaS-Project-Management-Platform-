package com.saasplatform.task.service;

import com.saasplatform.task.dto.TaskBoardResponse;
import com.saasplatform.task.dto.TaskRequest;
import com.saasplatform.task.model.Task;
import com.saasplatform.task.model.TaskStatus;
import com.saasplatform.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Task createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .projectId(request.getProjectId())
                .assigneeId(request.getAssigneeId())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .dueDate(request.getDueDate())
                .build();
        return repository.save(task);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, TaskRequest request) {
        Task task = getTaskById(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssigneeId(request.getAssigneeId());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public TaskBoardResponse getKanbanBoard(Long projectId) {
        Map<TaskStatus, List<Task>> columns = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            columns.put(status, repository.findByProjectIdAndStatus(projectId, status));
        }
        return TaskBoardResponse.builder()
                .projectId(projectId)
                .columns(columns)
                .build();
    }
}
