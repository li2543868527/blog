$(document).ready(function() {
    // ボタン使用できないになる
    $("#login").prop("disabled", true);

    // 入力変化があれば：
    $("input").on("input", function() {
        var userName = $("input[name='userName']").val();
        var password = $("input[name='password']").val();
        var email = $("input[name='email']").val();

        // 入力しない状態になっていないことを確認する
        if (userName !== "" && password !== "" && email !== "") {
            //　押せる処理
            $("#login").prop("disabled", false);
        } else {
            // 押せないになる処理
            $("#login").prop("disabled", true);
        }
    });

    $("#sendCodeButton").click(function() {
        var email = $("input[name='email']").val();
        if (email !== "") {
            $.post("/register/sendCode", { email: email }, function(response) {
                if (response === "success") {
                    alert("認証コードは発送しました、メールをチェックしてください");
                } else {
                    alert("認証コードの発送は失敗しました、正しいメールアドレスを確認してください");
                }
            });
        } else {
            alert("メールアドレスを入力してください");
        }
    });
});
