package com.springmvclearn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvclearn.model.Project;
import com.springmvclearn.service.ProjectManager;
@Transactional
@Service("ProjectManager")
public class ProjectServiceImpl implements ProjectManager {
	@Resource
	private ProjectManager projectmanager;

	@Override
	public void addProject(Project project) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProject(Project project) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProjectById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Project> findAll() {
		List<Project> ls = new ArrayList<Project>();
		ls = (ArrayList<Project>) projectmanager.findAll();
		return ls;
	}

	@Override
	public Project findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProjectManager getProjectmanager() {
		return projectmanager;
	}

	public void setProjectmanager(ProjectManager projectmanager) {
		this.projectmanager = projectmanager;
	}

}
