package com.wuzhengyu.conference.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wuzhengyu.conference.Model.Meeting;
import com.wuzhengyu.conference.Model.User;
import com.wuzhengyu.conference.Model.User_meeting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "Meeting Controller", description = "对Meeting对象的操作", tags = "Meeting")
@RequestMapping("/meetings")
public class MeetingController {

    @ApiOperation(value="getAllMeetings", notes="Get all meetings")
    @ResponseBody
    @RequestMapping(value = "/getAllMeetings", method = RequestMethod.GET)
    public Object getAllMeetings()
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> re =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        Object rst = re.getBody();
        return rst;
    }

    @ApiOperation(value="getPartialMeetings", notes="Get partial meetings")
    @ResponseBody
    @RequestMapping(value = "/getPartialMeetings", method = RequestMethod.GET)
    public Object getPartialMeetings()
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> re =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        Object rst = re.getBody();
        Logger logger = LoggerFactory.getLogger(MeetingController.class);
        logger.info("hhh:"+rst);

        return rst;
    }

    @ApiOperation(value="getRecommendedMeetings", notes="Get recommended meetings")
    @ResponseBody
    @RequestMapping(value = "/getRecommendedMeetings/{user_id}", method = RequestMethod.GET)
    public List<Meeting> getAllUser(@PathVariable("user_id") Integer user_id )
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> re =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        Object rst = re.getBody();
        Map<String, List<Meeting>> map = (Map<String,List<Meeting>>)rst;
        Logger logger = LoggerFactory.getLogger(User_meetingController.class);
        List<Meeting> meeting_list = map.get("Meeting");
        String url_user = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User/" + user_id;
        ResponseEntity<User> rt = restTemplate.getForEntity(url_user, User.class);
        User user = rt.getBody();
        List<Meeting> ret_list = new ArrayList<>();
        int count = 0;
        for (Meeting meeting: meeting_list){
            if (count > 9){
                break;
            }
            if(meeting.getTheme() == user.getInterest()){
                ret_list.add(meeting);
                count++;
            }
        }
        return ret_list;
    }

    @ApiOperation(value="getMeeting", notes="Get a meeting")
    @ResponseBody
    @RequestMapping(value = "/getMeeting/{id}", method = RequestMethod.GET)
    public Meeting getMeeting(@PathVariable("id") String id)
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/" +id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Meeting> re = restTemplate.getForEntity(url, Meeting.class);
        Meeting rst = re.getBody();
        return rst;
    }

    @ApiOperation(value="addMeeting", notes="Add a meeting")
    @ResponseBody
    @RequestMapping(value = "/addMeeting", method = RequestMethod.POST)
    public Meeting addMeeting(@RequestBody Meeting meeting)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Meeting> re = restTemplate.postForEntity("http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/", meeting, Meeting.class);
        Meeting rst = re.getBody();
        return rst;
    }

    @ApiOperation(value="deleteMeeting", notes="Delete a meeting")
    @ResponseBody
    @RequestMapping(value = "/deleteMeeting/{id}", method = RequestMethod.DELETE)
    public void deleteMeeting(@PathVariable("id") String id)
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Meeting/" +id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url, Meeting.class);
    }
}

