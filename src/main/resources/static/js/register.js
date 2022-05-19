$(document).ready(function () {
    let emailCheck = false;
    let passwordCheck = false;
    let nicknameCheck = false;

    $('#inputEmail').blur(function (){
        var email = $('#inputEmail').val();
        var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

        if(email.match(regExp) == null){
            htmlStr = '아이디는 이메일의 형식에 맞추어 작성해주세요.';
            $('#email-access').html(htmlStr);
            emailCheck = false;
            return false;
        }
        $('#email-access').html("");

        $.ajax({
            url: '/joinCheck',
            method: 'POST',
            dataType: 'json',
            data: {
                type: 'email',
                value: email
            },
            success: function (data) {
                if (data == 1) {
                    var htmlStr = '중복된 이메일 입니다.';
                    $('#email-access').html(htmlStr);
                    return false;
                } else if (data == 0) {
                    $('#email-access').html("");
                    emailCheck = true;
                    return true;
                }
            },
            error: function (e) {
                reject(e);
            }
        });
    });



    // password 창에서 Tab key가 눌렸을 떄
    $("#inputPassword").blur(function () {
        // if(e.keyCode === 9){
        var pw = $('#inputPassword').val();
        var num = pw.search(/[0-9]/g);
        var eng = pw.search(/[a-z]/ig);
        var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if (pw.length < 8) {
            var htmlStr = '비밀번호는 최소 8글자 이상 입력해주세요';
            $('#password-access').html(htmlStr);
            passwordCheck = false;
            return false;
        } else if (pw.search(/\s/) != -1) {
            var htmlStr = '비밀번호는 공백 없이 입력해주세요.';
            $('#password-access').html(htmlStr);
            passwordCheck = false;
            return false;
        } else if (num < 0 || eng < 0 || spe < 0) {
            var htmlStr = '대소문자, 숫자, 특수문자를 혼합하여 입력해주세요.';
            $('#password-access').html(htmlStr);
            passwordCheck = false;
            return false;
        } else {
            console.log('통과');
            $('#password-access').html("");
            passwordCheck = true;
            return true;
        }
    });

    // 비밀번호 확인 체크
    $('#inputPasswordConfirm').blur(function () {
        var pw = $('#inputPassword').val();
        var cpw = $('#inputPasswordConfirm').val();
        if (!(pw === cpw)) {
            var htmlStr = '비밀번호가 일치하지 않습니다.';
            $('#password-access').html(htmlStr);
            passwordCheck = false;
            return false;
        }
        $('#password-access').html("");
        passwordCheck = true;
        return true;
    });


    //닉네임 검증
    // nickname 검증(중복 <- server)
    // 특수문자 불가능, 글자 제한 4 ~ 8 글자, 금지 단어 체크
    const blockwords = ['바보', '멍청이', '욕설', '욕'];

    $('#inputFirstName').blur(function () {
        var nick = $('#inputFirstName').val();
        var spe = nick.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
        if (nick.length < 4 || nick.length > 8) {
            var htmlStr = '닉네임은 4 ~ 8글자 사이로 입력할 수 있습니다.';
            $('#nickname-access').html(htmlStr);
            nicknameCheck = false;
            return false;
        } else if (spe > 0) {
            var htmlStr = '닉네임에 특수문자는 입력할 수 없습니다.';
            $('#nickname-access').html(htmlStr);
            nicknameCheck = false;
            return false;
        } else if (nick.search(/\s/) != -1) {
            var htmlStr = '닉네임에는 공백을 입력할 수 없습니다.';
            $('#nickname-access').html(htmlStr);
            nicknameCheck = false;
            return false;
        }
        //닉네임 금칙어 체크
        var word;
        for (let i = 0; i < blockwords.length; i++) {
            if (nick.match(blockwords[i]) != null) {
                word = nick.match(blockwords[i]);
                var htmlStr = '닉네임에 ' + word + '는 사용이 금지되었습니다.';
                $('#nickname-access').html(htmlStr);
                return false;
                nicknameCheck = false;
            }
        }
        $.ajax({
            url: '/joinCheck',
            method: 'POST',
            dataType: 'json',
            data: {
                type: 'nickname',
                value: nick
            },
            success: function (data) {
                if (data == 1) {
                    var htmlStr = '중복된 닉네임 입니다.';
                    $('#nickname-access').html(htmlStr);
                    return false;
                } else if (data == 0) {
                    $('#nickname-access').html("");
                    nicknameCheck = true;
                    return true;
                }
            },
            error: function (e) {
                reject(e);
            }
        });
    });


    $('#join_btn').on('click', function () {

        if (passwordCheck == false) {
            alert('비밀번호를 확인하세요.');
            return false;
        } else if (nicknameCheck == false) {
            alert('닉네임을 확인하세요.');
            return false;
        } else if (emailCheck == false) {
            alert('이메일을 확인하세요.');
            return false;
        }
        if (passwordCheck && nicknameCheck && emailCheck) {
            $('#join-form').submit();
        }
    });
});