package az.shopery.filenet_ms.service.impl;

import static az.shopery.filenet_ms.util.constants.ExceptionConstants.FILE_NOT_FOUND;
import static az.shopery.filenet_ms.util.constants.ExceptionConstants.SOME_FILES_NOT_FOUND;

import az.shopery.filenet_ms.handler.exception.FileNotFoundException;
import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import az.shopery.filenet_ms.model.entity.File;
import az.shopery.filenet_ms.repository.FileRepository;
import az.shopery.filenet_ms.service.FileService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public SuccessResponse<Void> deleteFile(UUID fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new FileNotFoundException(FILE_NOT_FOUND);
        }

        fileRepository.deleteById(fileId);
        return SuccessResponse.of("File deleted successfully!");
    }

    @Override
    public SuccessResponse<Void> deleteFiles(DeleteFilesRequestDto deleteFilesRequestDto) {
        List<UUID> fileIds = deleteFilesRequestDto.getFileIds();
        List<UUID> existingFileIds = fileRepository.findAllById(fileIds)
                .stream()
                .map(File::getId)
                .toList();

        if (existingFileIds.size() != fileIds.size()) {
            throw new FileNotFoundException(SOME_FILES_NOT_FOUND);
        }

        fileRepository.deleteAllById(fileIds);
        return SuccessResponse.of("Files deleted successfully!");
    }
}
