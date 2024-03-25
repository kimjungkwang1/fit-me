package site.chachacha.fitme.domain.tag.dto;

import lombok.Getter;
import site.chachacha.fitme.domain.tag.entity.Tag;

@Getter
public class TagResponse {

    private Long id;
    private String name;

    public TagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
