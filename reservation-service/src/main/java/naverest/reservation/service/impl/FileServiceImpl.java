package naverest.reservation.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import naverest.reservation.dao.FileDao;
import naverest.reservation.domain.FileDomain;
import naverest.reservation.exception.MismatchJpegPngFormatException;
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
	
	@Override
	@Transactional(readOnly = false)
	public FileDomain create(FileDomain file) {
		return fileDao.insert(file);
	}
	@Override
	@Transactional(readOnly = false)
	public List<FileDomain> createFileList(Integer userId, MultipartFile[] images) {
		if(!isJpgOrPngImage(images)) {
			throw new MismatchJpegPngFormatException("이미지가 jpeg/png 형식이 아닙니다.");
		}
		List<FileDomain> fileList = new ArrayList<FileDomain>();
		
		String currentDate = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
		String formattedDate = baseDir + currentDate;

		File f = new File(formattedDate);
		if (!f.exists()) { 
			f.mkdirs(); 
		}
		
		for (MultipartFile file : images) {
			String uuid = UUID.randomUUID().toString();
			String saveFileName = formattedDate + File.separator + uuid;
																			
			try (InputStream in = file.getInputStream(); 
					BufferedInputStream bis = new BufferedInputStream(in, 1024);
					FileOutputStream fos = new FileOutputStream(saveFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);) {
				int read = 0;
				while ((read = in.read()) != -1) {
					bos.write(read);
				}
				bos.close();
			} catch (Exception ex) {
				log.error("{}",ex);
			}
			
			FileDomain fileDomain = new FileDomain();
			fileDomain.setContentType(file.getContentType());
			fileDomain.setCreateDate(new Date());
			fileDomain.setDeleteFlag(1);
			fileDomain.setFileLength((int) file.getSize());
			fileDomain.setFileName(file.getOriginalFilename());
			fileDomain.setSaveFileName(File.separator + currentDate + File.separator + uuid);
			
			fileDomain.setUserId(userId);
			fileList.add(fileDao.insert(fileDomain));
		}
		return fileList;
	}
	
	private boolean isJpgOrPngImage(MultipartFile[] images) {
		for(int i=0; i<images.length; i++){
			MultipartFile mpf  = images[i];
			if(!mpf.getContentType().equals("image/jpeg")&&!mpf.getContentType().equals("image/png")){
				return false;
			}		
		}
		return true;
	}
}
