/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 23:06
*/

package justdj.top.util;

import justdj.top.pojo.Kind;
import justdj.top.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigInteger;
import java.util.List;


public class KindHelper {

	private static KindService kindService;
	
	private static List<Kind> kindList ;
	
	private static List<String> kindNameList;
	
	private KindHelper(){
	
	}
	
	public static BigInteger getKindId(String kindName){
		if (null == kindList)
			kindList = kindService.selectAllKind();
		for (Kind a:kindList){
			if (a.getName().equals(kindName))
				return a.getId();
		}
		return null;
	}
	
	public static List <String> getKindNameList() {
		if (null == kindService)
			System.err.println("\n" +"1111111111"+ "\n");
		if (null == kindNameList)
			kindNameList = kindService.selectAllKindName();
		return kindNameList;
	}
	
	public static KindService getKindService() {
		return kindService;
	}
	
	public static void setKindService(KindService kindService) {
		if (null == kindService)
			KindHelper.kindService = kindService;
	}
}
