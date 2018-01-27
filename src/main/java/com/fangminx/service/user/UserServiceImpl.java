package com.fangminx.service.user;

import com.fangminx.entity.Role;
import com.fangminx.entity.User;
import com.fangminx.repository.RoleRepository;
import com.fangminx.repository.UserRepository;
import com.fangminx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByName(String userName) {
        User user = userRepository.findByName(userName);
        if(user == null){
            return null;
        }

        List<Role> roles = roleRepository.findRoleByUserId(user.getId());
        if(roles == null || roles.isEmpty()){
            throw new DisabledException("权限非法");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
        user.setAuthorityList(authorities);
        return user;
    }
}
