package com.labkaa.labdemo.repo;

import com.labkaa.labdemo.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    // Ви можете додати додаткові методи для роботи з UploadedFile, якщо потрібно
}
