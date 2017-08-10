package naverest.reservation.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import naverest.reservation.domain.FileDomain;

public interface FileService {
	public FileDomain create(FileDomain file);
	public List<FileDomain> createFileList(Integer userId, MultipartFile[] images);
	public FileDomain find(Integer id);
}
