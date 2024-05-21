package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao sellDao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll(){
		return sellDao.findAll();
	}
	public void saveOrUpdate(Seller sell) {
		if (sell.getId() == null) {
			sellDao.insert(sell);
		}else {
			sellDao.update(sell);
		}
	}
	public void remove(Seller sell) {
		sellDao.deleteById(sell.getId());
	}
}
