package top.yueshushu.learn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.ApiModel;
import top.yueshushu.learn.business.ExtMessageBusiness;
import top.yueshushu.learn.response.OutputResult;

/**
 * 扩展信息
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/extMessage")
@ApiModel("扩展信息")
public class ExtMessageController {
	@Resource
	private ExtMessageBusiness extMessageBusiness;
	
	@RequestMapping("/poem")
	public OutputResult poem() {
		return extMessageBusiness.poem();
	}
}
