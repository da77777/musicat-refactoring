package com.example.musicat.domain.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail {

    private Long id;
    private MetaFile file;

    public Thumbnail(MetaFile file) {
        this.file = file;
    }
}
