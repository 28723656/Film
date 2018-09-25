package com.java1234.run;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.java1234.entity.Film;
import com.java1234.service.FilmService;
import com.java1234.service.LinkService;
import com.java1234.service.WebSiteInfoService;
import com.java1234.service.WebSiteService;

@Component("startupRunner")
public class StartupRunner implements CommandLineRunner,ServletContextListener{

	private ServletContext application=null;
	
	@Resource
	private FilmService filmService;
	
	@Resource
	private WebSiteInfoService webSiteInfoService;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private WebSiteService webSiteService;
	
	@Override
	public void run(String... args) throws Exception {
		this.loadData();
	}
	
	/**
	 * 加载数据到applicaton缓存中
	 */
	public void loadData(){
		application.setAttribute("newestInfoList", webSiteInfoService.list(null, 0, 10)); // 最新10条电影动态
		Film film=new Film();
		film.setHot(1);
		application.setAttribute("newestHotFilmList", filmService.list(film, 0, 10)); // 获取最新10条热门电影
		application.setAttribute("newestIndexHotFilmList", filmService.list(film, 0, 32)); // 获取最新32条热门电影 首页显示用到
		application.setAttribute("newestWebSiteList", webSiteService.newestList(0, 10)); // 获取最新10条电影网站收录
		application.setAttribute("newestFilmList", filmService.list(null, 0, 10)); // 获取最新10条电影信息
		application.setAttribute("linkList", linkService.listAll()); // 查询所有友情链接
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		application=sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
