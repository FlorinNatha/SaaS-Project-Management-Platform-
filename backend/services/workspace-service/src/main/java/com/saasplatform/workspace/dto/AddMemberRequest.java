package com.saasplatform.workspace.dto;

import com.saasplatform.workspace.model.Role;
import lombok.Data;

@Data
public class AddMemberRequest {
    private Long userId;
    private Role role;
}
