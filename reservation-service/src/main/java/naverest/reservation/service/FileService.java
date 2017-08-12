package naverest.reservation.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import naverest.reservation.domain.FileDomain;

public interface FileService {
	FileDomain create(FileDomain file);
	List<FileDomain> createFileList(Integer userId, MultipartFile[] images);
	FileDomain find(Integer id);
}
