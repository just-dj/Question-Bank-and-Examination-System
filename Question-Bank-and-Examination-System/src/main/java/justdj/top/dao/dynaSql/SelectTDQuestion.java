/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.3
  Time: 22:46
  Info:
*/

package justdj.top.dao.dynaSql;

import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.Map;

public class SelectTDQuestion {
	
	public String selectTDQuestionByCondition(Map<String, Object> para){
		StringBuffer buffer = new StringBuffer();
		if (BigInteger.valueOf(-1).equals(para.get("testDatabaseId"))){
			buffer.append("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
					"from kind join question join test_database \n" +
					"on kind.id = kind_id and test_database_id = test_database.id \n"
			+ " where course_id =  "  + para.get("courseId") + " ");
			boolean temp = false;
			if (null != para.get("kindId") ){
				buffer.append(" and  kind.id = '" + para.get("kindId") +"'" );
			}
			if (null != para.get("keyWord")){
					buffer.append(" and question like '%" + para.get("keyWord") + "%' ");
			}
			
		}else{
			buffer.append("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
					"from kind join question join test_database \n" +
					"on kind.id = kind_id and test_database_id = test_database.id \n" +
					"where test_database.id =  '" + para.get("testDatabaseId") +"' "
			+ "and course_id = " + para.get("courseId") + " ");
			
			if (null != para.get("kindId") ){
				buffer.append(" and kind.id = '" + para.get("kindId") +"'" );
			}
			
			if (null != para.get("keyWord")){
				buffer.append(" and question like '%" + para.get("keyWord") + "%' ");
			}
			
		}
		buffer.append(" order by question.id");
		
		return buffer.toString();
	}
}
