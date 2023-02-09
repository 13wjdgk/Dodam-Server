package com.example.dodam.repository.inquiry;

import com.example.dodam.domain.inquiry.Inquiry;
//import org.springframework.beans.factory.annotation.Autowired;
import com.example.dodam.repository.inquiry.InquiryRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcInquiryRepository implements InquiryRepository {

    private static final String TABLE = "inquiry";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcInquiryRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id")
                .usingColumns("userId", "title", "content", "answer", "status", "category",
                        "createAt", "updateAt", "imgPath", "filename");
    }

//    @Override
//    public Inquiry save(Inquiry inquiry) {
//
//        inquiry.setCreateAt(LocalDateTime.now());
//        inquiry.setUpdateAt(LocalDateTime.now());
//        SqlParameterSource param = new BeanPropertySqlParameterSource(inquiry);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        inquiry.setId(key.longValue());
//        return inquiry;
//    }

    @Override
    public Inquiry save(Inquiry inquiry, MultipartFile file) throws IOException {
        if(file != null) {
            String imgPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid+"_"+file.getOriginalFilename();
            File saveFile = new File(imgPath, fileName);
            file.transferTo(saveFile);
            inquiry.setFileName(fileName);
            inquiry.setImgPath("/files/" + fileName);
        }
        inquiry.setCreateAt(LocalDateTime.now());
        inquiry.setUpdateAt(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(inquiry);
        Number key = jdbcInsert.executeAndReturnKey(param);
        inquiry.setId(key.longValue());
        return inquiry;
    }

    @Override
    public Optional<Inquiry> findById(Long id) {
        List<Inquiry> result = jdbcTemplate.query("select * from inquiry where id = ?",inquiryRowmapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Inquiry> deleteById(Long id) {
        jdbcTemplate.update("delete from inquiry where id = ?",id);
        return Optional.empty();
    }

    @Override
    public List<Inquiry> findAll(Long userId) {
        return jdbcTemplate.query("select * from inquiry where userId = ?", inquiryRowmapper(), userId); //userId에 해당하는 문의사항만 출력 select * from inquiry where userId = ?
    }

    @Override
    @Transactional
    public Inquiry update(Long id, Inquiry inquiry, MultipartFile file) throws IOException {

        inquiry.setUpdateAt(LocalDateTime.now());
        if(file != null) {
            String imgPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid+"_"+file.getOriginalFilename();
            File saveFile = new File(imgPath, fileName);
            file.transferTo(saveFile);
            inquiry.setFileName(fileName);
            inquiry.setImgPath("/files/" + fileName);
        }
        if(inquiry.getAnswer() != null){
            inquiry.setStatus(true);
        }
        jdbcTemplate.update("update inquiry set title = ?, content = ?, status = ?, imgPath = ?, fileName = ?, category = ?, answer = ? where id = ?",inquiry.getTitle(),inquiry.getContent(), inquiry.isStatus(), inquiry.getImgPath(),inquiry.getFileName(), inquiry.getCategory(),inquiry.getAnswer(),id);
        return inquiry;
    }


    private RowMapper<Inquiry> inquiryRowmapper() {
        return BeanPropertyRowMapper.newInstance(Inquiry.class);
    }
}

