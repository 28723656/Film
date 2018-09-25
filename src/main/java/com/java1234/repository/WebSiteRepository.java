package com.java1234.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.java1234.entity.WebSite;

/**
 * 收录电影网址Repository接口
 * @author Administrator
 *
 */
public interface WebSiteRepository extends JpaRepository<WebSite, Integer>,JpaSpecificationExecutor<WebSite>{

}
