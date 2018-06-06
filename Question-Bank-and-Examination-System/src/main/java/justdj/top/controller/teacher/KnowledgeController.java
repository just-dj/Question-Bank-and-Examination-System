/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:15
  Info:
*/

package justdj.top.controller.teacher;

import justdj.top.pojo.Knowledge;
import justdj.top.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.BigInteger;
import java.util.List;

@Controller
public class KnowledgeController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 知识点管理
	 */
	@RequestMapping(value = "/te/knowledge",method = RequestMethod.GET)
	public void knowledgeList(@RequestParam("id")BigInteger courseId,
	                          Model model){
		List<Knowledge> knowledgeList = courseService.selectKnowledgeByCourseId(courseId);
		
		model.addAttribute(knowledgeList);
		
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [knowledgeId, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 知识点管理 待完善 删除知识点 注意现在没有对知识点分级 分级的话要考虑级联删除
	 */
	@RequestMapping(value = "/te/knowledge/delete",method = RequestMethod.GET)
	public String deleteKnowledge(@RequestParam("courseId")BigInteger courseId,
			@RequestParam("id") BigInteger knowledgeId,
            RedirectAttributes redirectAttributes,
            Model model){
		int result = courseService.deleteKnowledge(knowledgeId);
		
		redirectAttributes.addFlashAttribute("id",courseId);
		return "redirect:/te/knowledge";

	}
	
	/**
	 *@author  ShanDJ
	 *@params [name, introduce, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 添加知识点 待完善
	 */
	@RequestMapping(value = "/te/knowledge/add",method = RequestMethod.POST)
	public String addKnowledge(@RequestParam("courseId")BigInteger courseId,
			@RequestParam("name")String name,
            @RequestParam("introduce")String introduce,
            RedirectAttributes redirectAttributes,
            Model  model){
			
		int result = courseService.addKnowledge(courseId,name,introduce);
		redirectAttributes.addFlashAttribute("id",courseId);
		return "redirect:/te/knowledge";
	}
}
