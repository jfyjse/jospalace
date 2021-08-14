package com.joffy.jospalace.service.implementation;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.UpdateUserRequestModel;
import com.joffy.jospalace.repository.UserRepository;
import com.joffy.jospalace.service.UserService;
import com.joffy.jospalace.shared.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user){
       if(userRepository.findByEmail(user.getEmail()) !=null) throw new RuntimeException(user.getEmail()+" exists");

        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String publicUserId= utils.generatedUserId(10);
        userEntity.setUserId(publicUserId);
        userEntity.setAccountStatus(true);

        UserEntity storedUserDetails= userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);
        return returnValue;
    }
    @Override
    public UserDto getUser(String email)
    {
        UserEntity userEntity =userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserById(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException(userId);

        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       UserEntity userEntity = userRepository.findByEmail(email);
       if (userEntity == null) throw new UsernameNotFoundException(email);
       if (userEntity.isAccountStatus() == false) throw new IllegalStateException(" account deactivated contact support");

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    public void deactivateAccount(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setAccountStatus(false);
        userRepository.save(userEntity);
    }

    public UserDto updateUser(UserDto user, String uid) {
        UserEntity userEntity = userRepository.findByUserId(uid);
        userEntity.setName(user.getName());
        userEntity.setLocation(user.getLocation());
        userEntity.setPhone(user.getPhone());
        userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity,user);

        return user;

    }
}
