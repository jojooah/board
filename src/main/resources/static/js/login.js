$login = {
    login: function () {
        var loginId = document.getElementById('loginId').value;
        var password = document.getElementById('password').value;


        //todo
        if (loginId === '') {
            alert('아이디 없음 문구')
        }

        if (password === '') {
            alert('비밀번호 없음 문구')
        }

        var url = '/api/member/login';
        var param = {
            email: loginId,
            password: password
        }

        $ajax.post(url, param, $login.callback, $login.errCallback);
    },

    callback: function (response) {
        console.log(response);
    },

    errCallback: function (response) {
        console.error(response);
    }


}