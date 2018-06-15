package com.android.androidWearService;

import java.util.List;

import com.android.androidWearModel.Add;


public interface BaseService {
	
	String addInfo(Add addInfo);
	
	List<Add> getAll();
	
	String delete(String id);
	
	Add findById(String id);
	
	String update(Add addInfo);

}
