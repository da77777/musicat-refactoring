package com.example.musicat.domain.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistNode {
    private Long id;
    private Playlist playlist;
    private Music music;
}
