package az.shopery.filenet_ms.service;

import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import java.util.UUID;

public interface FileService {
    SuccessResponse<Void> deleteFile(UUID fileIds);
    SuccessResponse<Void> deleteFiles(DeleteFilesRequestDto deleteFilesRequestDto);
}
