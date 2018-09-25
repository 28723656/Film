package com.java1234.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java1234.entity.WebSite;
import com.java1234.run.StartupRunner;
import com.java1234.service.WebSiteInfoService;
import com.java1234.service.WebSiteService;
import com.java1234.util.StringUtil;

/**
 * 收录电影网址Controller类
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/webSite")
public class WebSiteAdminController {

	@Resource
	private WebSiteService webSiteService;
	
	@Resource
	private WebSiteInfoService webSiteInfoService;
	
	@Resource
	private StartupRunner startupRunner;
	
	/**
	 * 分页查询收录电影网址
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public Map<String,Object> list(WebSite webSite,@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows)throws Exception{
		List<WebSite> webSiteList=webSiteService.list(webSite,page-1, rows);
		Long total=webSiteService.getCount(webSite);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("rows", webSiteList);
		resultMap.put("total", total);
		return resultMap;
	}
	
	/**
	 * 添加或者修改收录电影网址
	 * @param link
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public Map<String,Object> save(WebSite webSite)throws Exception{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		webSiteService.save(webSite);
		startupRunner.loadData();
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 删除收录电影网址
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public Map<String,Object> delete(@RequestParam(value="ids")String ids)throws Exception{
		String []idsStr=ids.split(",");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		boolean flag=true;
		for(int i=0;i<idsStr.length;i++){
			Integer webSiteId=Integer.parseInt(idsStr[i]);
			if(webSiteInfoService.getByWebSiteId(webSiteId).size()>0){
				flag=false;
			}else{
				webSiteService.delete(webSiteId);				
			}
		}
		if(flag){
			resultMap.put("success", true);			
		}else{
			resultMap.put("success", false);
			resultMap.put("errorInfo", "电影动态信息中存在电影网址信息，不能删除！");
		}
		startupRunner.loadData();
		return resultMap;
	}
	
	/**
	 * 下拉框模糊查询用到
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comboList")
	public List<WebSite> comboList(String q)throws Exception{
		if(StringUtil.isEmpty(q)){
			return null;
		}
		WebSite webSite=new WebSite();
		webSite.setUrl(q);
		return webSiteService.list(webSite, 0, 30); // 最多查询30条记录
	}
}
