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
});
