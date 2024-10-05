package com.example.blogback.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorR {

    private Integer errno;

    private String message;

    private Map<String,Object> data;

    public static EditorR success(String url){
        EditorR editorR = new EditorR();
        editorR.setData(new HashMap<>());
        editorR.getData().put("url",url);
        editorR.setErrno(0);
        editorR.setMessage("上传成功");

        return editorR;
    }

    public static EditorR fail(String message){
        EditorR editorR = new EditorR();
        editorR.setErrno(1);
        editorR.setMessage(message);

        return editorR;
    }
}
