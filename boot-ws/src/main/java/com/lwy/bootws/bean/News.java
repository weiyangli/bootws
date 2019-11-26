package com.lwy.bootws.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "bootnews",type = "news")
@Getter
@Setter
@Accessors(chain = true)
public class News implements Serializable {
    @Id
    private Long id;
    @Field(type= FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String title;
//    @Field(type= FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String content;
    private Long publishDate;
    private String publisher;
}
