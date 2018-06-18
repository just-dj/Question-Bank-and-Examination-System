/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 16:57
*/

package justdj.top.controller;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Question;
import justdj.top.pojo.User;
import justdj.top.service.ParseFileService;
import justdj.top.service.TestPaperService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController1 {
	
	@Autowired
	@Qualifier("parseFileService")
	private ParseFileService parseFileService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	
	private static final long MAXSIZE  = Long.valueOf(10 * 1024 * 1024);
	
	@RequestMapping("/test")
	public String test(){ 
		return "test";
	}
	
	
	@RequestMapping("/error")
	public String error(){
		return "error";
	}
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,
	                         @RequestParam(value = "author",required = false) String author,
	                         @RequestParam(value = "file") MultipartFile uploadFile,
							 RedirectAttributes redirectAttributes) throws Exception{
		if (!uploadFile.isEmpty()) {
			//判断文件大小
//			if (uploadFile.getSize() > MAXSIZE){
//				redirectAttributes.addFlashAttribute("message","文件最大尺寸10M");
//				return "redirect:/test";
//			}


//			保存文件路径
//			String path = request.getServletContext().getRealPath("/images/");
//			String fileName = uploadFile.getOriginalFilename();
//			File filePath = new File(path,fileName);
//			if (!filePath.getParentFile().exists()){
//				filePath.getParentFile().mkdirs();
//			}
			//保存文件
//			uploadFile.transferTo(new File(path+File.separator+fileName));
			
			//选择题 包括单选和多选,
//			List<Question> list  = parseFileService.parseSelectFile(uploadFile.getInputStream());
//			System.err.println(JSON.toJSONString(list));
			
			//问答题
			List<Question> list1 = parseFileService.parseJudgementFile(uploadFile.getInputStream());
			System.out.println(JSON.toJSONString(list1));
			
			redirectAttributes.addFlashAttribute("message","上传成功"+uploadFile.getOriginalFilename());
		} else {
			redirectAttributes.addFlashAttribute("message","上传失败,failed.");
		}
		
		return "redirect:/test";
	}
	
	
	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
	                                       @RequestParam("filename") String filename,
	                                       Model model) throws Exception{
//		下载文件路径
		String path = request.getServletContext().getRealPath("/images");
		File  file = new File( path + File.separator + filename);
//		logger.info("请求下载   ："  + path + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
//		下载显示的文件名，解决中文名称乱码问题
		String downLoadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
//		通知浏览器以attachment（下载）方式打开图片
		headers.setContentDispositionFormData("attachment",downLoadFileName);
//		application/octet-stream :二进制数据流，最常见的文件下载
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity <byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/exam")
	@ResponseBody
	public String upAnswer(){
		
		return "testPaper";
	}
}
