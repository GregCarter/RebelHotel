package edu.unlv.cs.rebelhotel.web;

import java.io.IOException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileUploadController {
	@RequestMapping(params = "upload", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
	public String uploadForm(Model model) {
		return "file/upload";
	}
	
	@RequestMapping(params = "upload", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
	public String uploadFormHandler(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		if (file.isEmpty()) {
			return "file/upload";
		}
		byte[] file_data = file.getBytes();
		model.addAttribute("file_data", new String(file_data).toString());
		return "file/show";
	}
}