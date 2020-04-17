package com.xupu.common.controller;

import com.xupu.common.Tool;

import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.IRedisService;
import com.xupu.xzqy.po.XZDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请求个人缓存的数据
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {


    @Autowired
    private IRedisService redisService;

    /**
     * 查找缓存的数据
     * @param userid 用户id
     * @param mark 缓存的标记
     * @return
     */
    @GetMapping("/findredis")
    public ResultData findRedis(String userid,String mark) {

        ResultData reultData =  checkUserIdAndMark(userid,mark);
        if(reultData != null){
           return  reultData;
        }
        String json = redisService.findByUserIdAndMark(Long.parseLong(userid),mark);
        if(Tool.isEmpty(json)){
            return new ResultData(Status.Success,"没有找到对象",null );
        }
        return new ResultData(Status.Success,"成功",json );
    }

    /**
     * 保存 或者 修改  redis
     * @param userid
     * @param mark
     * @param json
     * @return
     */
    @PostMapping("/updateredis")
    @ResponseBody
    public ResultData UpdateReis(String userid,String mark,String json) {
        ResultData reultData =  checkUserIdAndMark(userid,mark);
        if(reultData != null){
            return  reultData;
        }

        redisService.saveOrUpdate(Long.parseLong(userid),mark,json);
        return new ResultData(Status.Success,"成功",json );
    }

    /**
     * 传入 userid 和 mark 如果没有问题 返回的是 null
     * @param userid
     * @param mark
     * @return
     */
    private ResultData checkUserIdAndMark(String userid, String mark) {
        if(Tool.isEmpty(userid) || Tool.isEmpty(mark) ){
            return  new ResultData(Status.Error,"传入的是空值",null);
        }
        Long userIdNum =-1L;
        try {
            userIdNum =  Long.parseLong(userid);
        }catch (Exception e) {
            return  new ResultData(Status.Error,"传入的userid不是数值",null);
        }
        return  null;
    }

    /**
     * 得到选中的行政代码
     * @param userid
     * @param mark
     * @return
     */
    @RequestMapping(value = "/findselectxzdm")
    public ResultData  findSelectXZDM(String userid, String mark) {
        ResultData reultData =  checkUserIdAndMark(userid,mark);
        if(reultData != null){
            return  reultData;
        }
        List<XZDM> xzdms = redisService.findSelectXZDM(Long.parseLong(userid),mark);
        return  new ResultData(Status.Success,"成功",Tool.getGson().toJson(xzdms));
    }

}
