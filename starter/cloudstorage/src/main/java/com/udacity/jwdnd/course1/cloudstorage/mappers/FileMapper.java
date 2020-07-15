package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM Files WHERE fileid = #{fileid}")
    File selectFile(Integer fileid);

    @Insert("INSERT INTO Files (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    Integer insertFile(File file);

    @Delete("DELETE FROM Files WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);
}
