
// 入力値の長さを確認する関数
function valueCheck(obj, minLength, maxLength, msg){
	var result = false;

    //入力値が空であるかを確認
	if(obj.value === ""){
		alert(msg + "を入力してください！");
		obj.focus();
		result = true;
	}
	var length = obj.value.length;

    //入力値の長さが指定された範囲を超えているかを確認
	if(result === false && length < minLength || length > maxLength){
		alert(msg + "を" + minLength + "〜" + maxLength +"文字以内で入力してください！");
		obj.focus();
		result = true;
	}
	return result;
}

// メールアドレスの形式を確認する関数
function emailCheck(email){
    // メールアドレスの形式を表す正規表現
	const pattern = /^[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

    // メールアドレスの形式が正しいかを確認
	if(pattern.test(email.value) === false){
		alert("メールアドレスを確認してください！");
		email.focus();
		return true;
	}
    else return false;
}

// 日付文字列をフォーマットする関数
function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더해줌
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    // 希望する形式で日付と時間を組み合わせて返す
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`; // 원하는 포맷으로 조합
}
