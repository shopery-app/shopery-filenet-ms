package az.shopery.filenet_ms.service;

import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.request.SaveFileRequestDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    SuccessResponse<Void> deleteFile(UUID fileIds);
    SuccessResponse<Void> deleteFiles(DeleteFilesRequestDto deleteFilesRequestDto);
    SuccessResponse<Void> saveFile(MultipartFile multipartFile);
}
