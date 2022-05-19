package com.example.musicat.controller;

import com.example.musicat.domain.music.Link;
import com.example.musicat.domain.music.Music;
import com.example.musicat.domain.music.Playlist;
import com.example.musicat.exception.customException.EmptyFileException;
import com.example.musicat.service.member.MemberService;
import com.example.musicat.service.music.MusicApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.expr.Instanceof;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Slf4j
@Validated
@RestController
public class MusicController {

    private final MusicApiService musicApiService;
    private final MemberService memberService;


    @Autowired
    public MusicController(MusicApiService musicApiService, MemberService memberService) {
        this.musicApiService = musicApiService;
        this.memberService = memberService;
    }

    @GetMapping("musictest/{id}")
    public void musicTest(@PathVariable Long id) {
        System.out.println("musictest entered");
        musicApiService.retrieveMusicById(id);
    }

//    @PostMapping("musicpost")
//    public Music upload(@RequestParam(value = "audio") MultipartFile file, @RequestParam(value = "image") MultipartFile imagefile,
//                        @RequestParam(value = "title") @Size(min = 1, max = 1) String title, @RequestParam(value = "memberNo") int memberNo,
//                        @RequestParam(value = "articleNo") int articleNo) throws HttpClientErrorException {
//        return musicApiService.registerMusic(file, imagefile, title, memberNo, articleNo);
//    }

    @PostMapping("musicpost")
    public ModelAndView upload(@RequestParam(value = "audio") @NotNull MultipartFile file, @RequestParam(value = "image") @NotNull MultipartFile imagefile,
                               @RequestParam(value = "title") @Size(min = 1, max = 100, message="제목을 입력해주세요.") String title
            , @RequestParam(value = "memberNo") int memberNo) throws HttpClientErrorException {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/board/musicRegisterCheck");


        if(file.isEmpty())
            throw new EmptyFileException("음악 파일이 없습니다.");
        if(imagefile.isEmpty())
            throw new EmptyFileException("이미지 파일이 없습니다.");

        Music music = musicApiService.registerMusic(file, imagefile, title, memberNo);
        mv.addObject("music", music);

        return mv;
    }

    @DeleteMapping("musicDeleteArticle")
    public String deleteMusicArticle(int articleNo) {
        musicApiService.deleteMusicByArticleNo(articleNo);
        return "success";
    }

    @DeleteMapping("musicDelete")
    public Map<String, String> deleteMusic(Long musicId) {
        musicApiService.deleteByMusicId(musicId);
        Map<String, String> map = new HashMap<String, String>();
        //map.put("result", "hello");
        map.put("success", Integer.toString(1));
        return map;
    }

    // 플레이리스트 추가
    @PostMapping("/addplaylist")
    public ModelAndView addPlaylist(@RequestParam(name = "title") String title, @RequestParam(name = "id") int memberNo, @RequestParam("image") MultipartFile file) {
        ModelAndView mv =  new ModelAndView();
        log.info("title : " + title);
        Playlist playlist = new Playlist();
        playlist.setMemberNo(memberNo); playlist.setPlaylistName(title);
        log.info("playlist : " + playlist.getPlaylistName());
        musicApiService.createPlaylist(title, memberNo, file);
        mv.setView(new RedirectView("/myPage/Playlist/" + memberNo));
        return mv;
    }

    // 플레이리스트 삭제
    @DeleteMapping("deleteplaylist/{memberNo}/{playlistKey}")
    public ModelAndView removePlaylist(HttpServletRequest req, @PathVariable(name = "playKey") String playlistKey, @PathVariable(name = "memberNo")Integer memberNo) {
        ModelAndView mv = new ModelAndView();
        //MemberVO member = (MemberVO) req.getSession().getAttribute("principal"); 이게 제대로 된 방법
        log.info("no : " + playlistKey);
        musicApiService.deletePlaylist(memberNo, playlistKey);
        mv.setView(new RedirectView("/myPage/Playlist/" + memberNo));
        return mv;
    }


    // 특정 플레이리스트 안에 곡 넣기
    @PostMapping("/pushmusic/{playlistKey}")
    public List<Map<String, Object>> insertMusicIntoPlaylist(@RequestParam(name = "musicNos") int musicNos, @PathVariable String playlistKey) {
        //MemberVO member = (MemberVO) req.getSession().getAttribute("principal"); //이게 제대로 된 방법
        //int memberNo = 6; // 임시방편
        log.info("playlistKey : " + playlistKey);
        log.info("musicNo : " + musicNos);
        List<Integer> m = new ArrayList<Integer>();
        m.add(musicNos);
        List<Music> musics = musicApiService.pushMusic(m, playlistKey);

        List<Map<String, Object>> newMusicInfos = new ArrayList<Map<String, Object>>();
        try {

            for (int i = 0; i < musics.size(); ++i) {
                Map<String, Object> map = (Map<String, Object>) musics.get(i);
                map.put("memberNickname", memberService.retrieveMemberByManager((Integer) map.get("memberNo")).getNickname());
                newMusicInfos.add((Map<String, Object>) musics.get(i));
            }

        } catch(Exception e){
            log.error("플레이리스트 안에 곡 넣기 실패 {}", e);
        }

        //log.info("1 : " + musics.get(1).getLinks().get(1));
        //return musics;
        return newMusicInfos;
    }

    // 특정 플레이리스트 안의 곡 빼기
    @DeleteMapping("/pullmusic/{playlistKey}")
    public ModelAndView deleteMusicFromPlaylist(@PathVariable String playlistKey, @RequestParam(name = "musicNos") int musicNos, @RequestParam(name = "memberNo") int memberNo) {
        ModelAndView mav = new ModelAndView();
        log.info("musicNos : " + musicNos);
        log.info("playlistKey : " + playlistKey);
        List<Integer> m = new ArrayList<>();
        m.add(musicNos);
        musicApiService.pullMusic(m, playlistKey);
        if (!playlistKey.equals(memberNo+"pl1")) {
            mav.setView(new RedirectView("/myPage/Playlist/" + memberNo + playlistKey));
            return mav;
        }
        else {
            return null;
        }
    }

    // 특정 플레이리스트 안에 곡 넣기
    @PostMapping("/pushmusic2/{playlistKey}")
    public ModelAndView insertMusicIntoPlaylistReturnMAV(@RequestParam(name = "musicNo") int musicNos, @RequestParam(name = "memberNo") int memberNo, @PathVariable String playlistKey) {
        //public List<Map<String,Object>> insertMusicIntoPlaylist(@RequestParam("musi")/*@RequestParam(name = "musicNos") List<Integer> musicNos*/, @PathVariable String playlistKey) {
        //MemberVO member = (MemberVO) req.getSession().getAttribute("principal"); //이게 제대로 된 방법
        //int memberNo = 6; // 임시방편
        ModelAndView mav = new ModelAndView();
        List<Integer> m = new ArrayList<Integer>();
        m.add(musicNos);
        List<Music> musics = musicApiService.pushMusic(m, playlistKey);

        List<Map<String, Object>> newMusicInfos = new ArrayList<Map<String, Object>>();
        try {

            for (int i = 0; i < musics.size(); ++i) {
                Map<String, Object> map = (Map<String, Object>) musics.get(i);
                map.put("memberNickname", memberService.retrieveMemberByManager((Integer) map.get("memberNo")).getNickname());
                newMusicInfos.add((Map<String, Object>) musics.get(i));
            }

        } catch(Exception e){
            log.error("플레이리스트 안에 곡 넣기 실패 {}", e);
        }

        //log.info("1 : " + musics.get(1).getLinks().get(1));
        //return musics;
        mav.setView(new RedirectView("/myPage/Playlist/" + memberNo));
        return mav;
    }

    // 플레이리스트 수정
    @PostMapping("/changeplaylist/{playlistKey}")
    public ModelAndView changePlaylistName(@PathVariable String playlistKey, @RequestParam(name = "title") String title, @RequestParam(name="image") MultipartFile image) {
        ModelAndView mv = new ModelAndView();
        //MemberVO member = (MemberVO) req.getSession().getAttribute("principal"); 이게 제대로 된 방법
        //int memberNo = 6;
        log.info("playlistKey : " + playlistKey);
        int memberNo = musicApiService.updatePlaylistName(playlistKey, title, image);
        mv.setView(new RedirectView("/myPage/Playlist/" + memberNo));
        return mv;
    }

    // 플레이리스트 목록 불러오기
    @GetMapping("/getPlaylist/{memberNo}")
    public List<Playlist> getPlaylist(@PathVariable int memberNo) {
        log.info("member num : " + memberNo);
        List<Playlist> list = musicApiService.showPlaylist(memberNo);
        return list;
    }

    // 플레이리스트 상세 불러오기
    @GetMapping("getDetailPlaylist/{playlistKey}")
    public void getDetailPlaylist(@PathVariable String playlistKey) {
        //MemberVO member = (MemberVO) req.getSession().getAttribute("principal"); 이게 제대로 된 방법
        //String str = musicApiService.showDetailPlaylist(playlistNo);
    }

    // 플레이리스트 썸네일 미리보기 이미지 가져오기
    @GetMapping("/playlistTempImage/")
    public void getThumbnailImage(@RequestParam(name = "playlistNos") List<String> playlistNos){
    }

    @PutMapping("musicConnectArticle/{musicId}/{articleNo}")
    public void connectToArticle(@PathVariable(name="musicId") Long musicId, @PathVariable(name="articleNo") int articleNo) {
        log.info("musicId : " + musicId);
        log.info("articleNo : " + articleNo);

        musicApiService.connectToArticle(musicId, articleNo);
    }

    @GetMapping("retrieveMusics/{articleNo}")
    public List<Music> retrieveMusics(@PathVariable(name="articleNo") int articleNo){
        List<Music> musicList = musicApiService.retrieveMusics(articleNo);

        return musicList;
    }

    @GetMapping("retrieveMusicList/{playlistKey}")
    public List<Music> retrieveMusicList(@PathVariable(name="playlistKey") String playlistKey){
        List<Music> musics = musicApiService.showDetailPlaylist(playlistKey);

        log.info("retireve music list : " + musics.toString());
        return musics;
    }
}
