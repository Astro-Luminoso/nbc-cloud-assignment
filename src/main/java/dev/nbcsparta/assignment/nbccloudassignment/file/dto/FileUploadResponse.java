package dev.nbcsparta.assignment.nbccloudassignment.file.dto;


public record FileUploadResponse(String key) {

    public static FileUploadResponse from(String key) {
        return new FileUploadResponse(key);
    }
}