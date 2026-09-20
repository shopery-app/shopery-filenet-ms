package az.shopery.filenet_ms.service.impl;

import static az.shopery.filenet_ms.util.constants.ExceptionConstants.FILE_NOT_FOUND;
import static az.shopery.filenet_ms.util.constants.ExceptionConstants.FILE_SAVING_ERROR;
import static az.shopery.filenet_ms.util.constants.ExceptionConstants.SOME_FILES_NOT_FOUND;

import az.shopery.filenet_ms.handler.exception.FileNotFoundException;
import az.shopery.filenet_ms.handler.exception.FileSavingException;
import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.response.SaveFileResponseDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import az.shopery.filenet_ms.model.entity.File;
import az.shopery.filenet_ms.repository.FileRepository;
import az.shopery.filenet_ms.service.FileService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public SuccessResponse<Void> deleteFile(UUID fileId) {
        log.info("deleting file with id: {}", fileId);
        if (!fileRepository.existsById(fileId)) {
            throw new FileNotFoundException(FILE_NOT_FOUND);
        }

        fileRepository.deleteById(fileId);
        return SuccessResponse.of("File deleted successfully!");
    }

    @Override
    public SuccessResponse<Void> deleteFiles(DeleteFilesRequestDto deleteFilesRequestDto) {
        log.info("deleting files with ids: {}", deleteFilesRequestDto.getFileIds());
        Set<UUID> fileIds = new HashSet<>(deleteFilesRequestDto.getFileIds());
        Set<UUID> existingFileIds = fileRepository.findAllById(fileIds)
                .stream()
                .map(File::getId)
                .collect(Collectors.toSet());

        if (!existingFileIds.containsAll(fileIds)) {
            throw new FileNotFoundException(SOME_FILES_NOT_FOUND);
        }

        fileRepository.deleteAllById(fileIds);
        return SuccessResponse.of("Files deleted successfully!");
    }

    @Override
    public SuccessResponse<SaveFileResponseDto> saveFile(MultipartFile multipartFile) {
        log.info("saving file: {}", multipartFile.getOriginalFilename());
        try {
            File file = File.builder()
                    .contentType(multipartFile.getContentType())
                    .originalName(multipartFile.getOriginalFilename())
                    .size(multipartFile.getSize())
                    .content(multipartFile.getBytes())
                    .build();

            File savedFile = fileRepository.save(file);

            SaveFileResponseDto saveFileResponseDto = SaveFileResponseDto.builder()
                    .fileId(savedFile.getId())
                    .build();

            return SuccessResponse.of(saveFileResponseDto, "File saved successfully!");
        } catch (Exception exception) {
            log.error("error while reading file: ", exception);
            throw new FileSavingException(FILE_SAVING_ERROR);
        }
    }
}
