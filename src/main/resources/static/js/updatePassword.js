$(document).ready(function (){

    //비밀번호 입력 체크
    $(document).on('click', '#update-password-btn', function () {
        const newPassword = $('#newPassword').val();
        const checkPassword = $('#checkPassword').val();
        console.log('newPassword', newPassword);
        console.log('checkPassword', checkPassword);
        if (!(newPassword === checkPassword)) {
            $('#password-check-msg').text('비밀번호를 확인해 주세요.');
            console.log('실패');
            return false;
        }
        console.log('성공');
        $('#update-password-form').submit();
    });

    //비밀번호 별표 제거, 표시
    $(document).on('click','#pw1, #pw2', function(){
        console.log('별표 제거');
        $(this).prev('input').toggleClass('active');
        if($('input').hasClass('active')){
            $(this).attr('class', "fa fa-eye-slash fa-lg")
                .prev('input').attr('type', 'text');
        } else{
            $(this).attr('class', "fa fa-eye fa-lg")
                .prev('input').attr('type', 'password');
        }
    });
});