package dev.nbcsparta.assignment.nbccloudassignment.file.dto;

public record FileDownloadUrlResponse(String url) {

    public static FileDownloadUrlResponse from(String url) {
        return new FileDownloadUrlResponse(url);
    }

}
