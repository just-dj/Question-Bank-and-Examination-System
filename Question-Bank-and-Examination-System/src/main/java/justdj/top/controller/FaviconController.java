/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.1
  Time: 21:30
*/

package justdj.top.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FaviconController {
	@RequestMapping(value = "/favicon.ico")
	public String getFavicon(){
		return "redirect:/";
	}
}
