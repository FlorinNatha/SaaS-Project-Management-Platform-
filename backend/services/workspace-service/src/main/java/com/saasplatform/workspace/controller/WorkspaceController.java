package com.saasplatform.workspace.controller;

import com.saasplatform.workspace.dto.AddMemberRequest;
import com.saasplatform.workspace.dto.WorkspaceRequest;
import com.saasplatform.workspace.model.Workspace;
import com.saasplatform.workspace.model.WorkspaceMember;
import com.saasplatform.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(
            @RequestBody WorkspaceRequest request,
            @RequestHeader("userId") Long userId
    ) {
        return ResponseEntity.ok(workspaceService.createWorkspace(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> getMyWorkspaces(
            @RequestHeader("userId") Long userId
    ) {
        return ResponseEntity.ok(workspaceService.getWorkspacesForUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable Long id) {
        return ResponseEntity.ok(workspaceService.getWorkspaceById(id));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<WorkspaceMember> addMember(
            @PathVariable Long id,
            @RequestBody AddMemberRequest request
    ) {
        return ResponseEntity.ok(workspaceService.addMember(id, request));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<WorkspaceMember>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(workspaceService.getWorkspaceMembers(id));
    }
}
