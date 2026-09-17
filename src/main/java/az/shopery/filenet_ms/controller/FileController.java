package az.shopery.filenet_ms.controller;

import az.shopery.filenet_ms.model.dto.request.DeleteFilesRequestDto;
import az.shopery.filenet_ms.model.dto.shared.SuccessResponse;
import az.shopery.filenet_ms.service.FileService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
