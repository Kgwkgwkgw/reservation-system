package kgw.reservation.service;

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

import kgw.reservation.dao.FileDao;
import kgw.reservation.domain.FileDomain;

@Service
public class FileService {
	private FileDao fileDao;
	@Value("${naverest.imageUploadPath}")
	private String baseDir; // 이미지파일 다운로드할 기본 경로
	
	private final Logger log = LoggerFactory.getLogger(FileService.class);

	@Autowired
	public FileService(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Transactional(readOnly = false)
	public FileDomain create(FileDomain file) {
		return fileDao.insert(file);
	}

	@Transactional(readOnly = false)
	public List<FileDomain> createFileList( Integer userId,MultipartFile[] images) {
		// 현재 날짜 string으로 반환
		List<FileDomain> fileList = new ArrayList<FileDomain>();
		
		String currentDate = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd")
				.format(new Date());
		String formattedDate = baseDir + currentDate;

		File f = new File(formattedDate);
		if (!f.exists()) { // 파일이 존재하지 않는다면
			f.mkdirs(); // 해당 디렉토리를 만든다. 하위폴더까지 한꺼번에 만든다.
		}
		
		for (MultipartFile file : images) {
			
			String contentType = file.getContentType();
			String name = file.getName();
			String originalFilename = file.getOriginalFilename();
			long size = file.getSize();

			String uuid = UUID.randomUUID().toString();
			String saveFileName = formattedDate + File.separator + uuid; // file save path
																			
			// 실제 파일을 저장함.
			try (InputStream in = file.getInputStream(); FileOutputStream fos = new FileOutputStream(saveFileName)) {
				int readCount = 0;
				byte[] buffer = new byte[512];
				while ((readCount = in.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// File
			FileDomain fileDomain = new FileDomain();
			fileDomain.setContentType(contentType);
			fileDomain.setCreateDate(new Date());
			fileDomain.setDeleteFlag(1);
			fileDomain.setFileLength((int) size);
			fileDomain.setFileName(originalFilename);
			fileDomain.setSaveFileName(File.separator + currentDate + File.separator + uuid);
			// 해당부분은 로그인기능 구현후 추가 처리
			fileDomain.setUserId(userId);
		
			fileList.add(fileDao.insert(fileDomain));
		} // for
		return fileList;
	}

	public FileDomain find(Integer id) {
		return fileDao.selectById(id);
	}
}
