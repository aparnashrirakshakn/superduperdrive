package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM Credentials WHERE credentialid = #{credentialid}")
    Credential selectCredential(Integer credentialid);

    @Select("SELECT * FROM Credentials WHERE userid = #{userid}")
    List<Credential> selectAllCredentials(Integer userid);

    @Insert("INSERT INTO Credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer insertCredential(Credential credential);

    @Update("UPDATE Credentials SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM Credentials WHERE credentialid = #{credentialid}")
    void deleteCredential(Integer credentialId);
}
