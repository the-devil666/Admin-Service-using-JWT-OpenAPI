package com.pws.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pws.admin.ApiSuccess;
import com.pws.admin.dto.LoginDTO;
import com.pws.admin.dto.PermissionDTO;
import com.pws.admin.dto.SignUpDTO;
import com.pws.admin.dto.UpdatePasswordDTO;
import com.pws.admin.dto.UserBasicDetailsDTO;
import com.pws.admin.dto.UserRoleXrefDTO;
import com.pws.admin.entity.Module;
import com.pws.admin.entity.Permission;
import com.pws.admin.entity.Role;
import com.pws.admin.entity.Skill;
import com.pws.admin.entity.User;
import com.pws.admin.exception.config.PWSException;
import com.pws.admin.service.AdminService;
import com.pws.admin.utility.CommonUtils;
import com.pws.admin.utility.JwtUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Author Vinayak M
 * @Date 09/01/23
 */

@RestController
@RequestMapping("/")
public class AdminController {
	
	@Autowired
	private JwtUtil jwtUtil;
	  @Autowired
	    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminService adminService;

	 @ApiOperation(value = "Signup")
    @PostMapping("public/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpDTO signUpDTO) throws PWSException {
        adminService.UserSignUp(signUpDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.CREATED));
    }
    
	 @ApiOperation(value = "Authenticate")
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody LoginDTO loginDTO) throws Exception {
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword())
					);
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtUtil.generateToken(loginDTO.getUserName());
	}
	

	 @ApiOperation(value = "Update User-Password")
	@PutMapping("private/update/user/password")
	public ResponseEntity<Object> updateUserPassword(@RequestBody UpdatePasswordDTO userPasswordDTO)throws PWSException{
		adminService.updateUserPassword(userPasswordDTO);
		return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
	}

	 @ApiOperation(value = "Add Role")
    @PostMapping("private/role/add")
    public ResponseEntity<Object> addRole(@RequestBody Role role) throws PWSException {
        adminService.addRole(role);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

	 @ApiOperation(value = "Update Role")
    @PutMapping ("private/role/update")
    public ResponseEntity<Object> updateRole(@RequestBody Role role) throws PWSException {
        adminService.updateRole(role);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

	 @ApiOperation(value = "Fetch Role ById")
    @GetMapping ("private/role/fetch/by/id")
    public ResponseEntity<Object> fetchRoleById( @RequestParam Integer id) throws PWSException {
        Optional<Role> role = adminService.fetchRoleById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, role.get()));
    }

	 @ApiOperation(value = "Fetch All Role")
    @GetMapping("private/role/fetch/all")
    public ResponseEntity<Object> fetchAllRole() throws PWSException {
        List<Role> roleList = adminService.fetchAllRole();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, roleList));
    }

	 @ApiOperation(value = "Deactivate Or Activate Role ById")
    @PostMapping("private/role/activate/deactivate")
    public ResponseEntity<Object> deactivateOrActivateRoleById(@RequestParam Integer id, @RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateRoleById(id, flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Add Module")
    @PostMapping("private/module/add")
    public ResponseEntity<Object> addModule(@RequestBody Module module) throws PWSException {
        adminService.addModule(module);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Update Module")
    @PutMapping("private/module/update")
    public ResponseEntity<Object> updateRole(@RequestBody Module module) throws PWSException {
        adminService.updateRole(module);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Fetch All Module")
    @GetMapping("private/module/fetchall")
    public ResponseEntity<Object> fetchAllModule() throws PWSException {
        List<Module> modulelist = adminService.fetchAllModule();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, modulelist));
    }
    
	 @ApiOperation(value = "Fetch Module Byid")
    @GetMapping("private/module/fetch/id")
    public ResponseEntity<Object> fetchModuleById(@RequestParam Integer id) throws PWSException {
        Optional<Module> module= adminService.fetchModuleById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, module));
    }
    
	 @ApiOperation(value = "Deactivate Or Activate Module ById")
    @PostMapping("private/module/activate/inactivate")
    public ResponseEntity<Object> deactivateOrActivateModuleById(@RequestParam  Integer id,@RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateModuleById(id,flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Save Or Update UserXref")
    @PostMapping("private/save/update/userxref")
    public ResponseEntity<Object> saveOrUpdateUserXref(@RequestBody  UserRoleXrefDTO userRoleXrefDTO) throws PWSException {
        adminService.saveOrUpdateUserXref(userRoleXrefDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Deactivate Or Activate Assigned Role To User")
    @PostMapping("private/userxref/activate/deactivate/byuser")
    public ResponseEntity<Object> deactivateOrActivateAssignedRoleToUser(@RequestParam Integer id, @RequestParam Boolean flag) throws PWSException {
        adminService.deactivateOrActivateAssignedRoleToUser(id, flag);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    
	 @ApiOperation(value = "Fetch User By Role")
    @GetMapping("private/fetch/userbyrole")
    public ResponseEntity<Object> fetchUserByRole(@RequestParam Integer roleId) throws PWSException {
        List<User> user = adminService.fetchUserByRole(roleId);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, user));
    }
    
	 @ApiOperation(value = "Fetch User By Id")
    @GetMapping("private/fetch/fetchUserById")
    public ResponseEntity<Object> fetchUserById(@RequestParam Integer Id) throws PWSException {
    			adminService.fetchUserById(Id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

	 @ApiOperation(value = "Add Permission")
    @PostMapping("private/permmision/add")
    public ResponseEntity<Object> addPermission(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.addPermission(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Update Permission")
    @PutMapping("private/permmision/update")
    public ResponseEntity<Object> updatePermission(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.updatePermission(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
	 @ApiOperation(value = "Fetch All Permission")
    @GetMapping("private/permission/fetchall")
    public ResponseEntity<Object> fetchAllPermission() throws PWSException {
    	 List<Permission> permissionlist = adminService.fetchAllPermission();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, permissionlist ));
    }
    
	 @ApiOperation(value = "Fetch Permission By Id")
    @GetMapping("private/permission/fetchPermission/Id")
    public ResponseEntity<Object> fetchPermissionById(@RequestParam Integer id) throws PWSException {
    	Optional<Permission> optionalpermission = adminService.fetchPermissionById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, optionalpermission));
    }
    
	 @ApiOperation(value = "Deactivate Or Activate Permission By Id")
    @PutMapping("private/permission/activate/deactivate/byuser")
    public ResponseEntity<Object> deactivateOrActivatePermissionById(@RequestBody PermissionDTO permissionDTO) throws PWSException {
        adminService.deactivateOrActivatePermissionById(permissionDTO);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, permissionDTO));
    }
    
    
	 @ApiOperation(value = "Get User Basic Info After Successfull Login")
    @GetMapping("private/userdetails")
    public ResponseEntity<Object> getUserBasicInfoAfterLoginSuccess(@RequestParam  String email) throws PWSException{
        UserBasicDetailsDTO userBasicDetailsDTO = adminService.getUserBasicInfoAfterLoginSuccess(email);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, userBasicDetailsDTO));
    }
    
	 @ApiOperation(value = "Add Skill")
    @PostMapping("private/skill/add")
    public ResponseEntity<Object> addskill(@RequestBody Skill skill) throws PWSException {
        adminService.addskill(skill);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

	 @ApiOperation(value = "Update Skill")
    @PutMapping ("private/skill/update")
    public ResponseEntity<Object> updateskill(@RequestBody Skill skill) throws PWSException {
        adminService.updateskill(skill);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }

	 @ApiOperation(value = "Fetch Skill By Id")
    @GetMapping ("private/skill/fetch/by/id")
    public ResponseEntity<Object> fetchskillById( @RequestParam Integer id) throws PWSException {
        Optional<Skill> skill = adminService.fetchskillById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, skill.get()));
    }

	 @ApiOperation(value = "Fetch All Skill")
    @GetMapping("private/skill/fetch/all")
    public ResponseEntity<Object> fetchAllSkills() throws PWSException {
        List<Skill> skillList = adminService.fetchAllSkills();
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK, skillList));
    }
    
	 @ApiOperation(value = "Delete Skill By Id")
    @DeleteMapping ("private/skill/delete/by/id")
    public ResponseEntity<Object> deleteskillById(@RequestParam Integer id) throws PWSException {
     adminService.deleteskillById(id);
        return CommonUtils.buildResponseEntity(new ApiSuccess(HttpStatus.OK));
    }
    
    
}