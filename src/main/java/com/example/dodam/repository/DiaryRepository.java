package com.example.dodam.repository;

import com.example.dodam.domain.model.Diary;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository {
    Diary save(Diary diary);
    Optional<Diary>  findByDate(String date);
    List<Diary> findAll();
}
