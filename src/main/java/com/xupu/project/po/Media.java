package com.xupu.project.po;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

/**
 * 多媒体
 */
@Entity
@Table(name = "o_media")
public class Media {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")//columnDefinition="表注释"
    private Long id;
    /**
     * 多媒体类型 1:照片 ，2：视频，3：声音
     */
    @Column(nullable = false)
    @Expose
    private int mediaType;
    /**
     * 多媒体名称
     */
    @Column(nullable = false)
    @Expose
    private String name;
    @ManyToOne(cascade={CascadeType.REFRESH},optional=false)
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaType == media.mediaType &&
                Objects.equals(name, media.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaType, name);
    }
}
