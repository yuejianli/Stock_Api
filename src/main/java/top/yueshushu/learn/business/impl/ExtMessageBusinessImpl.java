package top.yueshushu.learn.business.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import top.yueshushu.learn.business.ExtMessageBusiness;
import top.yueshushu.learn.extension.ExtJobService;
import top.yueshushu.learn.extension.model.shici.PoemResponse;
import top.yueshushu.learn.mode.vo.extension.PoemVo;
import top.yueshushu.learn.response.OutputResult;

/**
 * 用途描述
 *
 * @author yuejianli
 * @date 2022-06-10
 */
@Service
public class ExtMessageBusinessImpl implements ExtMessageBusiness {
	@Resource
	private ExtJobService extJobService;
	
	@Override
	public OutputResult poem() {
		PoemResponse poemResponse = extJobService.getPoem();
		PoemVo poemVo = new PoemVo();
		BeanUtils.copyProperties(poemResponse, poemVo);
		return OutputResult.buildSucc(poemVo);
	}
}
