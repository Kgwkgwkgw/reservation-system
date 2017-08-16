package naverest.reservation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import naverest.reservation.dao.FileDao;
import naverest.reservation.domain.FileDomain;
import naverest.reservation.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	private FileDao fileDao;
	@Value("${naverest.imageUploadPath}")
	private String baseDir;
	
	private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	public FileServiceImpl(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public FileDomain find(Integer id) {
		return fileDao.selectById(id);
	}
}
