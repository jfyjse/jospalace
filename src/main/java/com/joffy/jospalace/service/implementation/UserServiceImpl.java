package com.joffy.jospalace.service.implementation;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.repository.UserRepository;
import com.joffy.jospalace.service.UserService;
import com.joffy.jospalace.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user){
       if(userRepository.findByEmail(user.getEmail()) !=null) throw new RuntimeException(user.getEmail()+" exists");

        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword("test");
        String publicUserId= utils.generatedUserId(30);
        userEntity.setUserId(publicUserId);

        UserEntity storedUserDetails= userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);
        return returnValue;
    }
}
