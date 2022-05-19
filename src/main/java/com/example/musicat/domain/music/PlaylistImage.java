package com.example.musicat.domain.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistImage {
    private Long id;
    private Playlist playlist;
    private MetaFile metaFile;
}
