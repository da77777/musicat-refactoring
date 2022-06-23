# 🎸 musicat

# 프로젝트 목적
- 사용자가 자신의 자작곡 혹은 멜로디를 공유하고, 음원을 활용하여 재창조한 음원을 서로 공유하며, 음악을 좋아하고 즐기는 사람들의 소통과 발전을 위해 관련 커뮤니티 역할에 대한 필요성으로 프로젝트를 수립

# 프로젝트 기대 효과
- 사용자가 공유한 음원 청음 가능
- 신인 프로듀서, 작곡가들의 커뮤니티 형성 및 의견 공유
- 장르 혹은 태그에 따라 상세한 정보 조회 가능
- 자신만의 플레이리스트 설정 가능

# 개발환경

![musicat개발환경](https://user-images.githubusercontent.com/84134297/161516388-841fcc22-8869-4ea7-b480-2b7879810424.png)

# 팀원 소개

|**팀원**|**github**|
|:------:|:---:|
|강종훈|https://github.com/jhk1231|
|김성중|https://github.com/Gapus|
|김연주|https://github.com/cocoada|
|박예나|https://github.com/Fidget278|
|양다예|https://github.com/da77777|
|편근형|https://github.com/jollypyun|

# REST API

|**REST API**|**github**|
|:------:|:---:|
|Msicat_audio|https://github.com/jollypyun/musicat_audio|  


<div><br><br><br><br></div>

# 홈페이지 소개  

🎧 웹사이트 : http://www.musicat.life/main


   ## 음악 (음악 등록/재생, 재생 목록, 플레이 리스트)
   - 사용자는 게시글을 등록할 때 음악 파일과 제목을 등록하여 자신이 만든 음원을 사람들에게 공유할 수 있다.
   <img src="https://user-images.githubusercontent.com/84134297/161533734-2fac28c7-dde2-4b5e-a2e5-49ba26f6d2b1.png" width ="50%" height ="50%"/>
   
   - 게시글 조회자는 음원이 등록된 게시글의 + 버튼을 눌러 사이트 하단에 위치한 자신의 재생목록에 음원을 추가 및 재생할 수 있다.
   <img src="https://user-images.githubusercontent.com/84134297/161533737-e134f8b6-68fa-4057-bf47-11b42e34f692.jpg" width ="50%" height ="50%"/>   
   
   - 사용자는 자신만의 플레이리스트를 만들 수 있다.
   - 재생목록에 넣었던 음원을 자신만의 플레이리스트에 옮겨서 관리할 수 있다.
   <img src="https://user-images.githubusercontent.com/84134297/161533783-31d3f84c-8892-4562-b2e1-2739d419ff94.jpg" width ="50%" height ="50%"/>

   ## 웹소켓을 이용한 실시간 기능 (채팅, 알림)
   - 사용자는 채팅방에 입장하여 다른 사용자들과 채팅을 할 수 있다. 
   <img src="https://user-images.githubusercontent.com/84134297/161534080-e9dece0c-ff2c-4fae-99a8-e596362a44c9.jpg" width ="50%" height ="50%"/>
   
   - 게시글에 댓글이 달리면 실시간으로 알림을 받을 수 있다.
   <img src="https://user-images.githubusercontent.com/84134297/161534089-62d1f2ee-9f63-47b3-9ad5-e6998c8cd2b6.jpg" width ="50%" height ="50%"/>
   
   ## 관리자 기능 (회원관리, 게시판관리, security 리소스 테이블)
   - 관리자는 회원 관리 페이지에서 회원의 정보를 조회하거나 활동 정지를 시킬 수 있다. 
   - 회원의 등급은 활동량에 따라 자동으로 조절된다.
   <img src="https://user-images.githubusercontent.com/84134297/161533579-2b37b7b1-4012-4ec4-b09c-d132864c8667.jpg" width ="50%" height ="50%"/>
   
   - 관리자는 게시판 관리 페이지에서 카테고리/게시판 정보를 수정하거나 추가할 수 있다.
   - 게시판마다 쓰기/읽기 등급을 지정해줄 수 있다. 
   <img src="https://user-images.githubusercontent.com/84134297/161533583-6fa26ff9-520a-4a8b-9036-cf17478758ad.jpg" width ="50%" height ="50%"/>   
   
   - 관리자는 리소스 관리 페이지에서 게시판별로 접근 권한을 관리할 수 있다.
   <img src="https://user-images.githubusercontent.com/84134297/161533593-cced46d2-cbf0-4f05-9bee-ac2be2e2d552.jpg" width ="50%" height ="50%"/>
   
   ## 카페 기능 (게시판 즐겨찾기, 게시글, 댓글, 프로필, 팔로우)
   - 사용자는 마음에 드는 게시판을 즐겨찾기할 수 있다. 즐겨찾기된 게시판은 사이드바 게시판 리스트의 최상단에 노출된다.
   <img src ="https://user-images.githubusercontent.com/84134297/161533347-e20f8d9f-ed5f-4bcf-9192-952a35373515.jpg" width="50%" height ="50%" />
   
   - 사용자는 글/이미지/첨부파일/음악 등을 작성하여 게시글을 등록할 수 있다.
   - 게시글에 댓글과 대댓글을 작성할 수 있다.
   <img src ="https://user-images.githubusercontent.com/84134297/161533430-8c8115d8-0793-4458-90cb-8f0ae3b4e250.jpg" width="50%" height ="50%" />
   
   - 사용자는 마음에 드는 음원을 올린 사람의 프로필을 조회하고 팔로우할 수 있다.
   <img src ="https://user-images.githubusercontent.com/84134297/161533455-5a25f171-aa6b-49fc-a30e-0f63af9c8684.jpg" width="50%" height ="50%" />
   

# 작성 문서

Musicat ERD
![musicat_erd](https://user-images.githubusercontent.com/46017367/170855414-233a20f5-5acd-4df0-aa25-c0ba8e43fe53.png)

Musicat_audio (음악 REST API 서버) ERD
![musicat_audio](https://user-images.githubusercontent.com/46017367/170855436-c2afebfc-7571-4d53-a5e6-3c714500b39d.png)

스토리보드 : https://docs.google.com/presentation/d/1CT5rOUXLF3YRw29W2SvhUJ4tkSGM_MsM/edit#slide=id.gf3581bb510_14_1901 <br>
클래스 다이어그램 : https://drive.google.com/drive/folders/1wkykMS0aJz2i1AP3EVlpRc1Rw4WYveEo <br>
오퍼레이션 명세서 : https://docs.google.com/spreadsheets/d/1k6uNxotW-iIw0QqDdqVFOyhwd-KssNH6/edit#gid=450068953 <br> 
유즈케이스 명세서 : https://docs.google.com/spreadsheets/d/1p2Rh-vJiGZ4zxu78tcTN96jTbrDz2hGm/edit#gid=960756777 <br>
