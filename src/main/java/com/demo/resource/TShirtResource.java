package com.demo.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.domain.TShirt;
import com.demo.service.TShirtService;

@RestController
@RequestMapping("/tshirt")
public class TShirtResource {

	@Autowired
	private TShirtService tshirtService;

	@PostMapping("/add")
	public TShirt addTshirtPost(@RequestBody TShirt tshirt) {
		return tshirtService.save(tshirt);
	}

	@PostMapping("/add/image")
	public ResponseEntity<String> upload(@RequestParam("id") Long id, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		try {
			TShirt tShirt = tshirtService.findOne(id);
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
			String fileName = id + ".png";

			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/tshirt/" + fileName)));
			stream.write(bytes);
			stream.close();

			return new ResponseEntity<String>("Upload Success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Upload Failed!", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/update/image")
	public ResponseEntity<String> uploadImagePost(@RequestParam("id") Long id, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			TShirt tshirt = tshirtService.findOne(id);
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());
			String fileName = id + ".png";

			Files.delete(Paths.get("src/main/resources/static/image/tshirt/" + fileName));

			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/tshirt/" + fileName)));
			stream.write(bytes);
			stream.close();

			return new ResponseEntity<String>("Upload Success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Upload Failed!", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/tshirtList")
	public List<TShirt> getTshirtList() {
		return tshirtService.findAll();
	}

	@PostMapping("/update")
	public TShirt updateTshirtPost(@RequestBody TShirt tShirt) {
		return tshirtService.save(tShirt);
	}

	@PostMapping("/remove")
	public ResponseEntity<String> remove(@RequestBody String id) throws IOException {
		tshirtService.removeOne(Long.parseLong(id));
		String fileName = id + ".png";

		Files.delete(Paths.get("src/main/resources/static/image/tshirt/" + fileName));

		return new ResponseEntity<String>("Remove Success!", HttpStatus.OK);
	}

	@RequestMapping("/{id}")
	public TShirt getTshirt(@PathVariable("id") Long id) {
		TShirt tShirt = tshirtService.findOne(id);
		return tShirt;
	}

	@PostMapping("/searchTshirt")
	public List<TShirt> searchTshirt(@RequestBody String keyword) {
		List<TShirt> tshirtList = tshirtService.blurrySearch(keyword);

		return tshirtList;
	}
}
