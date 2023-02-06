package com.example.dodam.repository;

import com.example.dodam.domain.model.Diary;
import com.example.dodam.domain.model.DiaryList;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository {
    Diary save(Diary diary);
    String updateDiary(Diary diary);
    String deleteDiary(Integer id);
    Optional<Diary>  findByDate(String date);
    List<DiaryList> findAll(Integer id);
}