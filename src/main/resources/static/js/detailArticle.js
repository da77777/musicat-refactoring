function writeForm() {
    location.href = "/articles/insert";
}

$(document).ready(function () {

    /*-------------------댓글 ------------------ */
    const getAjax = function (url, no, content, depth) {
        // resolve, reject는 자바스크립트에서 지원하는 콜백 함수이다.
        return new Promise((resolve, reject) => {
            $.ajax({
                url: url,
                method: 'POST',
                dataType: 'json',
                data: {
                    no: no,
                    content: content,
                    articleNo: $("#article_no").val(),
                    depth: depth
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (e) {
                    reject(e);
                }
            });
        });
    }

    const removeReply = function (url, no) {
        // resolve, reject는 자바스크립트에서 지원하는 콜백 함수이다.
        return new Promise((resolve, reject) => {
            $.ajax({
                url: url,
                method: 'GET',
                dataType: 'json',
                data: {
                    no: no,
                    articleNo: $("#article_no").val()
                },
                success: function (data) {
                    resolve(data);
                },
                error: function (e) {
                    console.trace();
                    reject(e);
                }
            });
        });
    }

    async function requestProcess(url, no, content, depth) {
        try {
            console.log("rP 접속");
            let replyList = null;
            if (content == null || content == '' || typeof content == "undefined") {
                replyList = await removeReply(url, no);
            } else {
                console.log("GetAjAx 전");
                replyList = await getAjax(url, no, content, depth);
                console.log("GetAjAx 후");
            }

            $('#replys').html("");
            let htmlStr = [];


            console.log(htmlStr);
            for (let i = 0; i < replyList.length; i++) {
                htmlStr.push('<div class="reply_list">');
                if (replyList[i].hide == 1) {
                    htmlStr.push('<p>삭제된 댓글 입니다.</p>');
                } else {
                    htmlStr.push('<div class="dropdown-divider"></div>');
                    if (replyList[i].depth == 1) {
                        htmlStr.push('<div class="reply-style ps-5" id=' + replyList[i].no + '>');
                    } else {
                        htmlStr.push('<div class="reply-style" id=' + replyList[i].no + '>');
                    }
                    htmlStr.push('<div class="reply-style" id=' + replyList[i].no + '>');
                    htmlStr.push('<div class="reply-info-style">');
                    htmlStr.push('<div>');
                    htmlStr.push('<span class="me-3 dropdown">');
                    htmlStr.push('<a class="dropdown-toggle fw-bold" data-bs-toggle="dropdown"> ' + replyList[i].nickname + '</a>');
                    htmlStr.push('<ul class="dropdown-menu">');
                    htmlStr.push('<li><a class="dropdown-item" href="|/myPage/Playlist/' + replyList[i].memberNo + '|>MyPage</a></li>');
                    htmlStr.push('<li><a class="dropdown-item" href="/notelist">쪽지함</a></li>');
                    htmlStr.push('</ul>');
                    htmlStr.push('</span>');
                    htmlStr.push('<span>' + replyList[i].writeDate + '</span>');
                    htmlStr.push('</div>');
                    htmlStr.push('<span class="Content reply-text-style">' + replyList[i].content + '</span>');
                    htmlStr.push('</div>');
                    htmlStr.push('<div class="mt-2 detail-replyBtn-style" id="' + replyList[i].groupNo + '">');
                    htmlStr.push('<input type="button" class="depth_reply_btn me-2" value="답글쓰기"/>');
                    if (replyList[i].memberNo == $('#login_no').val()) {
                        htmlStr.push('<input type="button" class="modify_Reply_Form_Btn me-2" value="수정" />');
                        htmlStr.push('<input type="button" class="remove_Reply_Btn me-2" value="삭제" />');
                    }
                    htmlStr.push('</div>');
                    htmlStr.push('</div>');
                }

                htmlStr.push('</div>');
            }
            console.log(htmlStr);
            $('#replys').html(htmlStr.join(""));


        } catch (error) {
            console.trace();
            console.log("error : ", error);
        }
    }

    console.log();

    //댓글 작성
    $('#write_reply_btn').on('click', function () {
        //const articleNo = $("#article_no").val();
        const content = $('#write_content').val();
        const depth = 0; // 최초 작성은 원글이라 depth는 0
        if (content == null || content === "") {
            $('#write_content').attr("placeholder", "내용을 입력하세요");
            $('#write_content').addClass('changeplaceholder');
            return false;
        }
        console.log('ajax전');
        requestProcess('/insertReply', 0, content, depth);
        $("#write_content").val(""); // textarea 비우기
        $('#write_content').removeClass('changeplaceholder');
        $("#write_content").attr('placeholder', '댓글을 입력해주세요');
        console.log('ajax후');
    });



    //답글 폼
    $(document).on('click', '.depth_reply_btn', function () {
        console.log("답글 폼 입장")
        const no = $(this).parents('div').attr('id');
        console.log('no::::::::::', no);
        // var grpCheck = $('#write_depth_reply_form').find('grp_no');
        var grpCheck = $('#grp_no').val();
        console.log('grpCheck', grpCheck);
        if (grpCheck != null) {
            $('#grp_no').empty();
        }
        $('#grp_no').val(no);
        console.log(333, $('#grp_no').val());

        $('#write_depth_reply_form').insertAfter($(this).parents('.reply-style'));
        console.log('insertAfter');
        $('#write_depth_reply_form').show();
    });

    //답글 작성
    $('#write_depth_reply_btn').on('click', function () {
        // const no = $(this).find('.grp_no').val(); // grp 값
        // const no = $('#write_depth_reply_form').find('#grp_no').val();
        const no = $('#grp_no').val();
        console.log('no값', no);
        const content = $('#write_depth_content').val();
        const depth = 1; // 답글은 depth:1
        if (content == null || content === "") {
            $('#write_depth_content').attr("placeholder", "내용을 입력하세요");
            $('#write_depth_content').addClass('changeplaceholder');
            return false;
        }
        console.log('ajax전');
        requestProcess('/insertReply', no, content, depth);
        $("#write_depth_content").val(""); // textarea 비우기
        $('#write_depth_content').removeClass('changeplaceholder');
        $("#write_depth_content").attr('placeholder', '댓글을 입력해주세요');
        // $('#write_depth_reply_form').insertAfter('#depth_reply_write_form_area');
        $('#write_depth_reply_form').insertAfter('#writeReplyForm');
        $('#write_depth_reply_form').hide();
        $('#write_depth_reply_form').html();
        console.log('ajax후');
    });


    //댓글 수정폼
    $(document).on('click', '.modify_Reply_Form_Btn', function () {
        const no = $(this).parents('.reply-style').attr('id');
        $('#modify_reply_form').insertAfter('#' + no);
        const content = $('#' + no).find('.Content').text();
        console.log('content:', content);
        $('#reply_content[placeholder]').val(content);
        $('#no').val(no);
        $('#modify_reply_form').show();
        $('#' + no).hide();
    });

    //답글 취소
    $('.depth_reply_cancle_btn').on('click', function () {
        $('#write_depth_reply_form').hide();
    });



    //댓글 수정폼 닫기
    $('.modify_cancle').on('click', function () {
        const no = $('#no').val();
        console.log('cancelModifyFormNo:', no);
        $('#' + no).show();
        $('#modify_reply_form').hide();
        $('#modify_reply_form').insertAfter('#writeReplyForm');
    });

    //댓글 수정
    $('.modify_reply_btn').on('click', function () {
        // 댓글No
        const no = $('#no').val();
        console.log('modifyActionNo:', no);
        const content = $('#' + no).parent('div').find('#reply_content').val();
        const depth = $(this).parents('table').find('.depth').val();
        if (content == null || content === "") {
            $('#reply_content').attr("placeholder", "내용을 입력하세요");
            $('#reply_content').addClass('changeplaceholder');
            return false;
        }
        console.log('modifyReplyContent:', content);
        requestProcess('/modifyReply', no, content, depth);
        $('#reply_content').removeClass('changeplaceholder');
        $('#modify_reply_form').insertAfter('#writeReplyForm');
        $('#modify_reply_form').hide();
        $('#modify_reply_form').html();
    });


    //댓글 삭제
    $(document).on('click', '.remove_Reply_Btn', function () {
        const no = $(this).parents('.reply-style').attr('id');
        console.log('remove', no)
        requestProcess('/removeReply', no);
    });


    $(document).on('click', '#add_like_btn', function () {
        $.ajax({
            url: '/articles/addLike',
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                "articleNo": $("#article_no").val()
            }),
            success: function (data) {
                let count = data.totalcount; // 총 추천 수
                //let count = JSON.parse(data).totalCount;
                // alert(count);
                let likecount = [];
                likecount += "<button type='button' id='del_like_btn'><i class='fas fa-heart fa-lg'></i> </button>";
                // likecount += "<strong>" + count + "</strong>";
                $('#like_area').html("");
                $('#like_area').html(likecount);
            },
            error: function (e) {
                console.trace();
                reject(e);
            }
        });
    });


    $(document).on('click', '#del_like_btn', function () {
        $.ajax({
            url: "/articles/delLike",
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                "articleNo": $("#article_no").val()
            }),
            success: function (data) {
                let count = data.totalcount; // 총 추천 수
                let likecount = [];
                likecount += "<button type='button' id='add_like_btn'><i class='far fa-heart fa-lg'></i></button>";
                // likecount += "<strong>" + count + "</strong>";
                $('#like_area').html("");
                $('#like_area').html(likecount);
            },
            error: function (e) {
                console.trace();
                reject(e);
            }
        });
    })

});

