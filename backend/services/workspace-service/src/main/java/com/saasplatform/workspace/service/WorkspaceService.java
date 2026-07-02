package com.saasplatform.workspace.service;

import com.saasplatform.workspace.dto.AddMemberRequest;
import com.saasplatform.workspace.dto.WorkspaceRequest;
import com.saasplatform.workspace.model.Role;
import com.saasplatform.workspace.model.Workspace;
import com.saasplatform.workspace.model.WorkspaceMember;
import com.saasplatform.workspace.repository.WorkspaceMemberRepository;
import com.saasplatform.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository memberRepository;

    @Transactional
    public Workspace createWorkspace(WorkspaceRequest request, Long ownerId) {
        Workspace workspace = Workspace.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(ownerId)
                .build();
        
        workspace = workspaceRepository.save(workspace);

        WorkspaceMember ownerMember = WorkspaceMember.builder()
                .workspace(workspace)
                .userId(ownerId)
                .role(Role.OWNER)
                .build();
        
        memberRepository.save(ownerMember);

        return workspace;
    }

    public List<Workspace> getWorkspacesForUser(Long userId) {
        return memberRepository.findByUserId(userId).stream()
                .map(WorkspaceMember::getWorkspace)
                .toList();
    }

    public Workspace getWorkspaceById(Long id) {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
    }

    @Transactional
    public WorkspaceMember addMember(Long workspaceId, AddMemberRequest request) {
        Workspace workspace = getWorkspaceById(workspaceId);

        if (memberRepository.findByWorkspaceIdAndUserId(workspaceId, request.getUserId()).isPresent()) {
            throw new RuntimeException("User is already a member of this workspace");
        }

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .userId(request.getUserId())
                .role(request.getRole() != null ? request.getRole() : Role.MEMBER)
                .build();

        return memberRepository.save(member);
    }
    
    public List<WorkspaceMember> getWorkspaceMembers(Long workspaceId) {
        return memberRepository.findByWorkspaceId(workspaceId);
    }
}
