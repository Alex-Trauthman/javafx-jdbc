package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao depDao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll(){
		return depDao.findAll();
	}
	public void saveOrUpdate(Department dep) {
		if (dep.getId() == null) {
			depDao.insert(dep);
		}else {
			depDao.update(dep);
		}
	}
	public void remove(Department dep) {
		depDao.deleteById(dep.getId());
	}
}
