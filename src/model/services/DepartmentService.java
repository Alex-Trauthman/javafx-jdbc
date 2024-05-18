package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {
	public List<Department> findAll(){
		List<Department> departmentList = new ArrayList<>();
		departmentList.add(new Department(1,"Eletronics"));
		departmentList.add(new Department(2,"Books"));
		departmentList.add(new Department(3, "Celphones"));
		return departmentList;
	}
}
