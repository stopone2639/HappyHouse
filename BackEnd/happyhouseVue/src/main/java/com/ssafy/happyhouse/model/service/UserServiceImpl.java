package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.mapper.UserMapper;
import com.ssafy.util.RandomPw;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto login(Map<String, String> map) throws Exception {
		String encodePw = userMapper.selectId(map.get("userid"));
		System.out.println(encodePw);
		Map<String, String> map2 =  new HashMap<String, String>();
		map2.put("userid", map.get("userid"));
		map2.put("userpw", encodePw);
		boolean check = passwordEncoder.matches(map.get("userpw"), encodePw);
		System.out.println(check);
		if(check) {
			return userMapper.login(map2);
		} 
		return null;
	}

	@Override
	public void registUser(UserDto userDto) throws Exception {
		String encodePw = passwordEncoder.encode(userDto.getUserpw());
		userDto.setUserpw(encodePw);
		userMapper.registUser(userDto);	
	}

	@Override
	public List<UserDto> listUser() throws Exception {
		return userMapper.listUser();
	}

	@Override
	public int updateUser(UserDto userDto) throws Exception {
		String encodePw = passwordEncoder.encode(userDto.getUserpw());
		userDto.setUserpw(encodePw);
		return userMapper.updateUser(userDto);
	}

	@Override
	public void deleteUser(String userid) throws Exception {
		userMapper.deleteUser(userid);
	}

	@Override
	public int idCheck(String checkId) throws Exception {
		return userMapper.idCheck(checkId);
	}

	@Override
	public String idFind(Map<String, String> map) throws Exception {
		return userMapper.idFind(map);
	}
	
	@Override
	public String pwFind(Map<String, String> map) throws Exception{
		RandomPw randomPwd = new RandomPw();
		UserDto userDto = userMapper.pwFind(map);
		if(userDto != null) {
			String ranPw = randomPwd.randomPw();
			String encodePw = passwordEncoder.encode(ranPw);
			userDto.setUserpw(encodePw);
			int i = userMapper.updateUser(userDto);
			if(i == 1) {
				return ranPw;
			}
			return null;
		}
		return null;
	}
}