package dev.nbcsparta.assignment.nbccloudassignment.common;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("Member not found. id=" + id);
    }
}
