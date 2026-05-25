package dev.nbcsparta.assignment.nbccloudassignment.file.controller;

import dev.nbcsparta.assignment.nbccloudassignment.file.dto.FileDownloadUrlResponse;
import dev.nbcsparta.assignment.nbccloudassignment.file.dto.FileUploadResponse;
import dev.nbcsparta.assignment.nbccloudassignment.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{id}/profile-image")
public class FileController {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<FileUploadResponse> upload (
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        String key = s3Service.upload(id, file);
        return ResponseEntity.ok(FileUploadResponse.from(key));
    }

    @GetMapping
    public ResponseEntity<FileDownloadUrlResponse> getDownloadUrl (
            @PathVariable Long id) {
        URL url = s3Service.getDownloadUrl(id);
        return ResponseEntity.ok(FileDownloadUrlResponse.from(url.toString()));
    }
}
