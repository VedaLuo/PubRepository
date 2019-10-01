package com.lzc.model;

import lombok.Data;

@Data
public class SysGroupRoleUserDTO {
	
	private String groupId;
	private String groupName;
	private String roleId;
	private String roleName;
	
	private String realName;
	private String email;
	private String tel;
	
	private Integer spanGroup;
	private Integer spanRole;

}
