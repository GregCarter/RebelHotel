package edu.unlv.cs.rebelhotel.web;

import java.io.File;
import java.io.IOException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.file.DefaultStudentService;
import edu.unlv.cs.rebelhotel.file.FileUpload;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;

@RequestMapping("/file")
@Controller
public class FileUploadController {
	@RequestMapping(params = "upload", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,'ROLE_SUPERUSER')")
	public String uploadForm(Model model){
		return "file/upload";
	}
	
    @RequestMapping(params = "upload", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,'ROLE_SUPERUSER')")
    public String uploadFormHandler(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    	if (file.isEmpty()){
    		return "file/upload";
    	}
    	byte[] fileData = file.getBytes();
    	file.transferTo(new File("./data/", file.getOriginalFilename()));
    	DefaultStudentService defaultStudentService = new DefaultStudentService();
		defaultStudentService.upload();
    	model.addAttribute("fileData", new String(fileData).toString());
    	return "file/show";
    }
}