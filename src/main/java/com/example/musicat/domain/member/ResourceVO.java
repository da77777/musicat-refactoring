package com.example.musicat.domain.member;


import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("resourceVo")
public class ResourceVO {
    private int resourceNo;
    private String resourceName;
    private String resourceType;

    private List<ResourceVO> resourceList = new ArrayList<>();
}
