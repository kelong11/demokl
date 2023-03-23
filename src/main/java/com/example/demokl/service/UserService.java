package com.example.demokl.service;

import com.example.demokl.dao.UserMapper;
import com.example.demokl.entity.User;
import com.example.demokl.util.CommunityUtil;
import com.example.demokl.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;

@Service
public class UserService implements CommunityConstant{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;//控制给前端传值的bean，thymeleaf

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(User user){
        Map<String,Object> map=new HashMap<>();
        //空值处理
        if (user == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }

        //验证用户名是否为空
        User u = userMapper.selectByName(user.getUsername());
        if (u!=null){
            map.put("usernameMsg","该用户名已存在");
            return map;
        }

        //验证邮箱是否为空
        u = userMapper.selectByEmail(user.getEmail());
        if (u!=null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }

        //注册用户
        //设置随机字符串
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        //设置拼接随机字符串的密码，并加密
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        //设置用户类型，0普通用户
        user.setType(0);
        //设置激活状态，0未激活
        user.setStatus(0);
        //设置激活码，激活码也随机生成
        user.setActivationCode(CommunityUtil.generateUUID());
        //从牛客网给用户生成一个用户随机头像
        //使用String.format可以将new Random().nextInt(1000)生成的0-1000的随机整数代替http://images.nowcoder.com/head/%dt.png中的%d占位符
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        //给用户发送激活邮件
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        //http://localhost:8080/community/activation/101/code
        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content=templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账户",content);
        //最后返回map,如果没有问题map即为空，代表没有问题
        return map;
    }

    //激活方法
    public  int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        if (user.getStatus()==1){
            return ACTIVATION_REPEAT;
        }else if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FALLURE;
        }
    }
}
