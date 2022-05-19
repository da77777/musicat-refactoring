$(document).ready(function () {

    // const audioTime = sessionStorage.getItem("audioTime");
    const audioSrc = sessionStorage.getItem("audioSrc");

    // console.log("audioTime : " + audioTime);
    console.log("audioSrc : " + audioSrc);

    if(audioSrc != null) {

        audio.src = audioSrc;

        // playlistPlay();
    }


});