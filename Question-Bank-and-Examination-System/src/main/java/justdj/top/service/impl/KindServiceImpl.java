/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 23:02
*/

package justdj.top.service.impl;

import justdj.top.dao.KindMapper;
import justdj.top.pojo.Kind;
import justdj.top.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("kindService")
public class KindServiceImpl implements KindService {
	
	@Autowired
	private KindMapper  kindMapper;
	
	@Override
	public List<Kind> selectAllKind() {
		return kindMapper.selectAllKind();
	}
	
	@Override
	public List <String> selectAllKindName() {
		return kindMapper.selectAllKindName();
	}
}
