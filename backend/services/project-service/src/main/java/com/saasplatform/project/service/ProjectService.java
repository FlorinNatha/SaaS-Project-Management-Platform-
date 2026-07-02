package com.saasplatform.project.service;

import com.saasplatform.project.dto.ProjectRequest;
import com.saasplatform.project.model.Project;
import com.saasplatform.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;

    public Project createProject(ProjectRequest request, Long userId) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceId(request.getWorkspaceId())
                .createdBy(userId)
                .build();
        return repository.save(project);
    }

    public List<Project> getProjectsByWorkspace(Long workspaceId) {
        return repository.findByWorkspaceId(workspaceId);
    }

    public Project getProjectById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project updateProject(Long id, ProjectRequest request) {
        Project project = getProjectById(id);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return repository.save(project);
    }

    public void deleteProject(Long id) {
        repository.deleteById(id);
    }
}
