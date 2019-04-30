package com.wuzhengyu.conference.Controller;

import com.wuzhengyu.conference.Model.User;
import com.wuzhengyu.conference.Model.User_meeting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "User_meeting Controller", description = "对User_meeting对象的操作", tags = "User_meeting")
@RequestMapping("/user_meeting")
public class User_meetingController {

    @ApiOperation(value="changeUserState", notes="Change user state")
    @ResponseBody
    @RequestMapping(value = "/register/{meeting_id}/{user_id}", method = RequestMethod.GET)
    public List<User_meeting> getAllUser(@PathVariable("meeting_id") Integer meeting_id, @PathVariable("user_id") Integer user_id )
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User_meeting/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> re =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        Object rst = re.getBody();
        Map<String, List<User_meeting>> map = (Map<String,List<User_meeting>>)rst;
        Logger logger = LoggerFactory.getLogger(User_meetingController.class);
        List<User_meeting> um = map.get("User_meeting");
        for (User_meeting user_meeting:um){
            if(user_meeting.getMeeting_id() == meeting_id && user_meeting.getUser_id() ==user_id){
                user_meeting.setRole("1");
                restTemplate.put("http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User/", user_meeting, User_meeting.class);


            }
        }

        return um;
    }

    @ApiOperation(value="addUser_meeting", notes="Add a user_meeting")
    @ResponseBody
    @RequestMapping(value = "/addUser_meeting", method = RequestMethod.POST)
    public User_meeting addUser(@RequestBody User_meeting user_meeting)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User_meeting> re = restTemplate.postForEntity("http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User_meeting/", user_meeting, User_meeting.class);
        User_meeting rst = re.getBody();
        return rst;
    }

}
