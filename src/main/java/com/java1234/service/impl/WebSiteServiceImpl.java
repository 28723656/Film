package com.java1234.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.java1234.entity.WebSite;
import com.java1234.repository.WebSiteRepository;
import com.java1234.service.WebSiteService;
import com.java1234.util.StringUtil;

/**
 * 收录电影网址Service实现类
 * @author Administrator
 *
 */
@Service("webSiteService")
public class WebSiteServiceImpl implements WebSiteService{

	@Resource
	private WebSiteRepository webSiteRepository;
	
	@Override
	public List<WebSite> list(WebSite webSite, Integer page, Integer pageSize) {
		Pageable pageable=new PageRequest(page, pageSize,Sort.Direction.ASC,"id");
		Page<WebSite> pageWebSite=webSiteRepository.findAll(new Specification<WebSite>() {
			
			@Override
			public Predicate toPredicate(Root<WebSite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(webSite!=null){
					if(StringUtil.isNotEmpty(webSite.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+webSite.getName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(webSite.getUrl())){
						predicate.getExpressions().add(cb.like(root.get("url"), "%"+webSite.getUrl().trim()+"%"));
					}
				}  
				return predicate;
			}
		}, pageable);
		return pageWebSite.getContent();
	}

	@Override
	public Long getCount(WebSite webSite) {
		Long count=webSiteRepository.count(new Specification<WebSite>() {

			@Override
			public Predicate toPredicate(Root<WebSite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(webSite!=null){
					if(StringUtil.isNotEmpty(webSite.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+webSite.getName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(webSite.getUrl())){
						predicate.getExpressions().add(cb.like(root.get("url"), "%"+webSite.getUrl().trim()+"%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(WebSite webSite) {
		webSiteRepository.save(webSite);
	}

	@Override
	public void delete(Integer id) {
		webSiteRepository.delete(id);
	}

	@Override
	public List<WebSite> newestList(Integer page, Integer pageSize) {
		Pageable pageable=new PageRequest(page, pageSize,Sort.Direction.DESC,"id");
		webSiteRepository.findall 
		return webSiteRepository.findAll(pageable).getContent();
	}

}
