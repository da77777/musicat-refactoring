package com.example.musicat.domain.music;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistNode {
    private Long id;
    private Playlist playlist;
    private Music music;
}
