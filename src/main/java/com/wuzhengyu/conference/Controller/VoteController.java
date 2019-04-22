package com.wuzhengyu.conference.Controller;

import com.wuzhengyu.conference.Model.Meeting;
import com.wuzhengyu.conference.Model.User;
import com.wuzhengyu.conference.Model.Vote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Api(value = "Vote Controller", description = "对Vote对象的操作", tags = "Vote")
@RequestMapping("/vote")
public class VoteController {

    @ApiOperation(value="getAllVote", notes="Get all votes")
    @ResponseBody
    @RequestMapping(value = "/getAllVotes", method = RequestMethod.GET)
    public Object getAllVote()
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Vote/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> re =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        Object rst = re.getBody();
        return rst;
    }

    @ApiOperation(value="addVote", notes="Add a vote")
    @ResponseBody
    @RequestMapping(value = "/addVote", method = RequestMethod.POST)
    public Vote addVote(@RequestBody Vote vote)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Vote> re = restTemplate.postForEntity("http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/Vote/", vote, Vote.class);
        Vote rst = re.getBody();
        return rst;
    }

/*
    @ApiOperation(value="getUser", notes="Get a user")
    @ResponseBody
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") String id)
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User/" +id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> re = restTemplate.getForEntity(url,User.class);
        User rst = re.getBody();
        return rst;
    }


    @ApiOperation(value="deleteUser", notes="Delete a user")
    @ResponseBody
    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") String id)
    {
        String url = "http://47.103.7.215:8080/Entity/U1a72d5104e6d9/Conference/User/" +id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url, User.class);
    }*/
}

