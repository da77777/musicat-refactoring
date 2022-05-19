package com.example.musicat.domain.music;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    private String id;
    private Integer memberNo;
    private String playlistName;
    private List<PlaylistNode> playlistNodes = new ArrayList<>();
    private PlaylistImage playlistImage;
    private List<Link> links;
}
