package com.example.musicat.repository.etc;

import com.example.musicat.domain.etc.ChatRoomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomVO> chatRoomDTOMap;

    @PostConstruct // 한번만 실행되기 위해 사용
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomVO> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<ChatRoomVO> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoomVO findRoomById(String id){
        return chatRoomDTOMap.get(id);
    }

    public ChatRoomVO createChatRoomDTO(String name){
        ChatRoomVO room = ChatRoomVO.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }

    public String deleteChatRoom(String chatRoomId) {

        if(chatRoomDTOMap.remove(chatRoomId) == null) {
            log.info("room not exist");
            return "room not exist";
        }
        else{
            log.info("delete success");
            return "delete Success";
        }

    }
}