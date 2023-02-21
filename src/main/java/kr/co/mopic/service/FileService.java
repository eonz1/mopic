package kr.co.mopic.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.mopic.dto.request.FileAddDTO;
import kr.co.mopic.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3Service amazonS3Service;

    private final FileMapper fileMapper;

    /**
     * 파일 업로드
     * @param fileList
     * @return
     */
    @Transactional
    public void uploadImage(List<MultipartFile> fileList, int seq, Long userId) {

        for(MultipartFile file : fileList) {
            String fileName = createFileName(file.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Service.uploadFile(inputStream, objectMetadata, fileName);
            } catch (IOException ioe) {
                throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다 (%s)", file.getOriginalFilename()));
            }

            // 내부 DB에 파일 등록
            String url = amazonS3Service.getFileUrl(fileName);

            FileAddDTO fileAddDTO = FileAddDTO.builder()
                                                .fileName(fileName)
                                                .url(url)
                                                .boardType("MODEL")
                                                .boardSeq(seq)
                                                .userId(userId)
                                                .build();

            fileMapper.insertFile(fileAddDTO);
        }
    }

    /**
     * 파일 삭제
     * @param fileName
     * @return
     */
    public void deleteImage(String fileName) {

        // S3 물리파일 삭제
        amazonS3Service.deleteFile(fileName);

        // 내부 DB 파일 삭제
        fileMapper.deleteFile(fileName);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
