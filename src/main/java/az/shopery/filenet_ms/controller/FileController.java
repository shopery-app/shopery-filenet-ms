package az.shopery.filenet_ms.controller;

import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.response.GetFileResponseDto;
import az.shopery.filenet_ms.model.dto.response.SaveFileResponseDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import az.shopery.filenet_ms.service.FileService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> deleteFile(@PathVariable UUID id) {
        return ResponseEntity.ok(fileService.deleteFile(id));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<Void>> deleteFiles(@RequestBody @Valid DeleteFilesRequestDto deleteFilesRequestDto) {
        return ResponseEntity.ok(fileService.deleteFiles(deleteFilesRequestDto));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<SaveFileResponseDto>> saveFile(@RequestPart("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(fileService.saveFile(multipartFile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<GetFileResponseDto>> getFile(@PathVariable UUID id) {
        return ResponseEntity.ok(fileService.getFile(id));
    }
}
