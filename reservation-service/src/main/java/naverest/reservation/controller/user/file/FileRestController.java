package naverest.reservation.controller.user.file;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import naverest.reservation.annotation.LogginedUser;
import naverest.reservation.domain.FileDomain;
import naverest.reservation.domain.User;
import naverest.reservation.exception.MismatchJpegPngFormatException;
import naverest.reservation.service.FileService;
import naverest.reservation.service.UserCommentService;

@RestController
@RequestMapping("/api")
public class FileRestController {
	private UserCommentService userCommentService;
	private FileService fileService;
	private final Logger log = LoggerFactory.getLogger(FileRestController.class);

	@Autowired
	public FileRestController(UserCommentService userCommentService, FileService fileService) {
		this.userCommentService = userCommentService;
		this.fileService = fileService;
	}
}
