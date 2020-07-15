package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM Notes WHERE noteid = #{noteid}")
    Note selectNote(Integer noteid);

    @Select("SELECT * FROM Notes WHERE userid = #{userid}")
    List<Note> selectAllNotes(Integer userid);

    @Insert("INSERT INTO Notes (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insertNote(Note note);

    @Update("UPDATE Notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteid}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM Notes WHERE noteid = #{noteid}")
    void deleteNote(Integer noteid);
}
